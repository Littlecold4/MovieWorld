package com.example.movieworld;

import com.example.movieworld.movie.dto.MovieResDto;
import com.example.movieworld.movie.repository.MovieRepository;
import com.example.movieworld.movie.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        int pageNum = 0;

        when(movieRepository.getMovieList(0)).thenReturn(expectedResult);
    }

    @Nested
    @DisplayName("Service _ Movie 리스트 조회 _ 성공")
    public class success{
        @Test
        @DisplayName("Success _ getMovieList")
        void success_getMovieList(){
            Page<MovieResDto> actualResult = movieService.getMovieList(0);

            assertNotNull(actualResult);
            assertEquals(expectedResult,actualResult);
            verify(movieRepository,times(1)).getMovieList(0);
        }
    }
}
