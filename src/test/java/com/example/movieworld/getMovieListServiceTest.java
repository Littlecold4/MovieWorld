package com.example.movieworld;

import com.example.movieworld.common.CustomException;
import com.example.movieworld.common.ErrorCode;
import com.example.movieworld.movie.dto.MovieResDto;
import com.example.movieworld.movie.repository.MovieRepository;
import com.example.movieworld.movie.service.MovieService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("test")
public class getMovieListServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Pageable pageable;
    Page<MovieResDto> expectedResult;

    @BeforeEach
    void setup(){
    }

    @Nested
    @DisplayName("Service _ Movie 리스트 조회 _ 성공")
    public class Success{
        @Test
        @DisplayName("성공")
        void success_getMovieList(){
            //given : 총 25개의 Movie가 있다고 가정
            long totalMovies = 25L;
            pageable = PageRequest.of(0,10);
            expectedResult = new PageImpl<>(createMovieResDto(10),pageable,totalMovies);
            when(movieRepository.getMovieList(0)).thenReturn(expectedResult);

            //when
            Page<MovieResDto> actualResult = movieService.getMovieList(0);

            assertNotNull(actualResult);
            assertEquals(expectedResult.getContent(),actualResult.getContent());
            assertEquals(expectedResult.getTotalElements(),actualResult.getTotalElements());

            verify(movieRepository,times(1)).getMovieList(0);
        }

        @Test
        @DisplayName("성공 _ 너무 큰 페이지 넘버가 들어온 경우, 마지막 페이지 반환")
        void success_Exceed_Page_Number(){
            int exceedPageNum = Integer.MAX_VALUE;
            //given : 총 25개의 Movie가 있다고 가정
            long totalMovies = 25;
            int lastPageNum = 25 / 10 ;// 총 페이지의 수
            pageable = PageRequest.of(lastPageNum,10);

            expectedResult = new PageImpl<>(createMovieResDto(5),pageable,totalMovies); // 마지막 페이지는 5개의 movie를 가짐
            when(movieRepository.count()).thenReturn(totalMovies);
            when(movieRepository.getMovieList(lastPageNum)).thenReturn(expectedResult);

            //when
            Page<MovieResDto> actualResult = movieService.getMovieList(exceedPageNum);

            //then
            assertNotNull(actualResult);
            assertEquals(expectedResult.getContent(),actualResult.getContent());
            assertEquals(expectedResult.getTotalElements(),actualResult.getTotalElements());

            verify(movieRepository,times(1)).getMovieList(lastPageNum);
            verify(movieRepository,times(1)).count();





        }
    }
    @Nested
    @DisplayName("Service _ Movie 리스트 조회  _ 실패")
    public class Failure{
        @Test
        @DisplayName("실패 _ 페이지 넘버 오류")
        void fail_Invalid_Page_Number(){
            int invalidPageNum =-1;

            Exception ex = assertThrows(CustomException.class,
                    ()-> movieService.getMovieList(invalidPageNum));
            assertEquals(ErrorCode.INVALID_PAGE_NUMBER.getMessage(),
                    ex.getMessage());

            verify(movieRepository, never()).getMovieList(anyInt());
        }
    }

    private List<MovieResDto> createMovieResDto(int num){
        List<MovieResDto> movieResDtoList = new ArrayList<>();
        for(int i =0 ;i<num; i++){
            MovieResDto testMovie = new MovieResDto().builder()
                    .movieId((long)num)
                    .title("TEST_title_"+num)
                    .build();

            movieResDtoList.add(testMovie);
        }
        return movieResDtoList;
    }
}
