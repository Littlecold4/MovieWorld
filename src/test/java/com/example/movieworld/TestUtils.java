package com.example.movieworld;

import com.example.movieworld.movie.Movie;
import com.example.movieworld.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestUtils {
    @Autowired
    private MovieRepository movieRepository;

    public void addMockMovie(int num){
        List<Movie> mockMovieList = new ArrayList<>();
        for(int i=0; i<num;i++){
            Movie movie = new Movie().builder()
                    .title("TEST_title_"+i)
                    .overview("TEST_overview_"+i)
                    .adult(false)
                    .releaseDate("TEST_releaseDate_"+i)
                    .posterPath("TEST_posterPath_"+i)
                    .build();

            mockMovieList.add(movie);
        }
        movieRepository.saveAll(mockMovieList);
    }
}
