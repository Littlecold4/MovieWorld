package com.example.movieworld;

import com.example.movieworld.genre.Genre;
import com.example.movieworld.genre.GenreRepository;
import com.example.movieworld.movie.domain.Movie;
import com.example.movieworld.movie.dto.MovieListResDto;
import com.example.movieworld.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TestUtils {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;

    public List<Movie> addMockMovie(int num,List<Long> genreIdList){
        List<Movie> mockMovieList = new ArrayList<>();
        for(int i=0; i<num;i++){
            Movie movie = new Movie().builder()
                    .title("TEST_title_"+i)
                    .overview("TEST_overview_"+i)
                    .adult(false)
                    .releaseDate("TEST_releaseDate_"+i)
                    .posterPath("TEST_posterPath_"+i)
                    .build();

            for(int j=0; j<genreIdList.size(); j++){
                Optional<Genre> genre = genreRepository.findById(genreIdList.get(j));
                MovieGenre movieGenre = new MovieGenre(movie,genre.get());
                movie.addMovieGenre(movieGenre);
                genre.get().addMovieGenre(movieGenre);
            }

            mockMovieList.add(movie);
        }
        return movieRepository.saveAll(mockMovieList);
    }

    public List<Genre> addMockGenre(int num){
        List<Genre> mockGenreList = new ArrayList<>();
        for(int i=0; i<num; i++){
            Genre genre =new Genre((long) i,"TEST_genre_"+i);

            mockGenreList.add(genre);
        }
        return genreRepository.saveAll(mockGenreList);
    }

    public List<MovieListResDto> createMovieResDto(int num){
        List<MovieListResDto> movieListResDtoList = new ArrayList<>();
        for(int i =0 ;i<num; i++){
            MovieListResDto testMovie = new MovieListResDto().builder()
                    .movieId((long)num)
                    .title("TEST_title_"+num)
                    .build();

            movieListResDtoList.add(testMovie);
        }
        return movieListResDtoList;
    }
}
