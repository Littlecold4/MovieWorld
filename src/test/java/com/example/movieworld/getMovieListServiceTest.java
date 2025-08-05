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

import java.util.Arrays;

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

    private final Pageable pageable = PageRequest.of(0,10);
    Page<MovieResDto> expectedResult;

    @BeforeEach
    void setup(){
        MovieResDto testMovie1 = new MovieResDto().builder()
                .movieId(0L)
                .title("TEST_title_0")
                .build();

        MovieResDto testMovie2 = new MovieResDto().builder()
                .movieId(1L)
                .title("TEST_title_1")
                .build();

        expectedResult =  new PageImpl<>(Arrays.asList(testMovie1,testMovie2),pageable,2);

        when(movieRepository.getMovieList(anyInt())).thenReturn(expectedResult);
    }

    @Nested
    @DisplayName("Service _ Movie 리스트 조회 _ 성공")
    public class Success{
        @Test
        @DisplayName("성공")
        void success_getMovieList(){
            Page<MovieResDto> actualResult = movieService.getMovieList(0);

            assertNotNull(actualResult);
            assertEquals(expectedResult,actualResult);
            verify(movieRepository,times(1)).getMovieList(0);
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
        }
    }
}
