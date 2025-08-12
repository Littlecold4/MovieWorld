package com.example.movieworld.movie.service;

import com.example.movieworld.TestUtils;
import com.example.movieworld.common.CustomException;
import com.example.movieworld.common.ErrorCode;
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
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class getMovieListByGenreServiceTest {
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MovieService movieService;
    @Autowired
    private TestUtils testUtils;

    private Pageable pageable ;

    Page<MovieListResDto> expectedResult;

    @BeforeEach
    void setup(){
        testUtils.addMockGenre(10);
    }

    @Nested
    @DisplayName("Service _ 장르별 Movie List 조회 _  성공")
    public class Success{
        @Test
        @DisplayName("성공")
        void success_getMovieListByGenre(){
            //given : 총 25개의 Movie가 있다고 가정
            long totalMovies = 25L;
            pageable = PageRequest.of(0,10);
            expectedResult = new PageImpl<>(testUtils.createMovieResDto(10),pageable,totalMovies);
            when(movieRepository.getMovieListByGenre(anyLong(),anyInt())).thenReturn(expectedResult);

            //when
            Page<MovieListResDto> actualResult = movieService.getMovieListByGenre(0L,2);

            assertNotNull(actualResult);
            assertEquals(expectedResult.getContent(),actualResult.getContent());
            assertEquals(expectedResult.getTotalElements(),actualResult.getTotalElements());

            verify(movieRepository,times(1)).getMovieListByGenre(0L,2);
        }

        @Test
        @DisplayName("성공 _ 너무 큰 페이지 넘버가 들어온 경우, 마지막 페이지 반환")
        void success_Exceed_Page_Number(){
            int exceedPageNum = Integer.MAX_VALUE;
            //given : 총 25개의 Movie가 있다고 가정
            long totalMovies = 25;
            int lastPageNum = 25/10;
            pageable = PageRequest.of(lastPageNum,10);

            expectedResult = new PageImpl<>(testUtils.createMovieResDto(5),pageable,totalMovies); // 마지막 페이지는 5개의 movie를 가짐

            when(movieRepository.count()).thenReturn(totalMovies);
            when(movieRepository.getMovieListByGenre(0L,lastPageNum)).thenReturn(expectedResult);

            //when
            Page<MovieListResDto> actualResult = movieService.getMovieListByGenre(0L,exceedPageNum);

            //then
            assertNotNull(actualResult);
            assertEquals(expectedResult.getContent(),actualResult.getContent());
            assertEquals(expectedResult.getTotalElements(),actualResult.getTotalElements());

            verify(movieRepository,times(1)).count();
            verify(movieRepository,times(1)).getMovieListByGenre(0L,exceedPageNum);
        }
    }

    @Nested
    @DisplayName("Service _ 장르별 Movie List 조회 _  실패")
    public class Failure{
        @Test
        @DisplayName("실패 _ 잘못된 장르Id")
        void fail_Invalid_Genre(){
            Long invalidGenreId = -1L;

            Exception ex = assertThrows(CustomException.class,
                    ()-> movieService.getMovieListByGenre(invalidGenreId,0));
            assertEquals(ErrorCode.INVALID_GENRE_ID.getMessage(),
                    ex.getMessage());

            verify(movieRepository,never()).getMovieListByGenre(anyLong(),anyInt());


        }

        @Test
        @DisplayName("실패 _ 페이지 넘버 오류")
        void fail_Invalid_Page_Number(){
            int invalidPageNum = -1;

            Exception ex = assertThrows(CustomException.class,
                    ()->movieService.getMovieListByGenre(0L,invalidPageNum));
            assertEquals(ErrorCode.INVALID_PAGE_NUMBER.getMessage(),
                    ex.getMessage());

            verify(movieRepository,never()).getMovieListByGenre(anyLong(),any());
        }
    }
}
