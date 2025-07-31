package com.example.movieworld;

import com.example.movieworld.movie.domain.Movie;
import com.example.movieworld.movie.repository.MovieRepository;
import com.example.movieworld.movie.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class getMovieListService {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie testMovie1;
    private Movie testMovie2;

    @BeforeEach
    void setup(){
        testMovie1 = mock(Movie.class); // Movie 클래스가 복잡할 경우 Mocking하여 사용합니다.
        when(testMovie1.getMovieId()).thenReturn(1L);
        when(testMovie1.getTitle()).thenReturn("테스트 영화 1");

        testMovie2 = mock(Movie.class);
        when(testMovie2.getMovieId()).thenReturn(2L);
        when(testMovie2.getTitle()).thenReturn("테스트 영화 2");
    }
}
