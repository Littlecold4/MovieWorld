package com.example.movieworld.movie.service;

import com.example.movieworld.TestUtils;
import com.example.movieworld.common.CustomException;
import com.example.movieworld.common.ErrorCode;
import com.example.movieworld.movie.dto.MovieListBySearchResDto;
import com.example.movieworld.movie.dto.MovieListResDto;
import com.example.movieworld.movie.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class getMovieListBySearchServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Pageable pageable;

    MovieListBySearchResDto expectedResult;
    @Autowired
    private TestUtils testUtils;


    @BeforeEach
    void setup(){
        testUtils.addMockGenre(10);
    }
    @Nested
    @DisplayName("Service _ 검색하여 Movie리스트 조회")
    public class Success{
        @Test
        @DisplayName("성공")
        void success_getMovieListBySearch(){
            //given : 총 25개의 Movie가 있다고 가정
            long totalMovies = 25L;
            String keyword = "keyword";
            pageable = PageRequest.of(0,10);
            Page<MovieListResDto> expectedList =new PageImpl<>(testUtils.createMovieResDto(10),pageable,totalMovies) ;
            expectedResult = new MovieListBySearchResDto(expectedList,keyword);

            when(movieRepository.getMovieListBySearch(anyString(),anyInt())).thenReturn(expectedList);

            //when
            MovieListBySearchResDto actualResult = movieService.getMovieListBySearch(keyword,0);

            //then
            assertNotNull(actualResult);
            assertEquals(expectedList,actualResult.getMovieListResDto());
            assertEquals(expectedResult.getKeyword(),actualResult.getKeyword());

            verify(movieRepository,times(1)).getMovieListBySearch(keyword,0);
        }

        @Test
        @DisplayName("성공 _ 너무 큰 페이지 넘버가 들어온 경우, 마지막 페이지 반환")
        void success_Exceed_Page_Number(){
            int exceedPageNum = Integer.MAX_VALUE;
            //given : 총 25개의 Movie가 있다고 가정
            long totalMovies = 25;
            int lastPageNum = 25/10;
            String keyword = "keyword";

            pageable = PageRequest.of(lastPageNum,10);

            Page<MovieListResDto> expectedList =new PageImpl<>(testUtils.createMovieResDto(10),pageable,totalMovies) ;
            expectedResult = new MovieListBySearchResDto(expectedList,keyword);


            when(movieRepository.count()).thenReturn(totalMovies);
            when(movieRepository.getMovieListBySearch(anyString(),lastPageNum)).thenReturn(expectedList);

            //when
            MovieListBySearchResDto actualResult = movieService.getMovieListBySearch(keyword,exceedPageNum);

            //then
            assertNotNull(actualResult);
            assertEquals(expectedList,actualResult.getMovieListResDto());
            assertEquals(expectedResult.getKeyword(),actualResult.getKeyword());

            verify(movieRepository,times(1)).getMovieListBySearch(keyword,0);
        }
    }
    @Nested
    @DisplayName("Service _ 검색하여 Movie List 조회 _  실패")
    class Failure{
        @Test
        @DisplayName("실패 _ 페이지 넘버 오류")
        void fail_Invalid_Page_Number(){
            int invalidPageNum = -1;
            String keyword = "keyword";

            Exception ex = assertThrows(CustomException.class,
                    ()-> movieService.getMovieListBySearch(keyword,invalidPageNum));
            assertEquals(ErrorCode.INVALID_PAGE_NUMBER.getMessage(),
                    ex.getMessage());

            verify(movieRepository,never()).getMovieListBySearch(anyString(),anyInt());
        }
    }
}
