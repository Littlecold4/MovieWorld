package com.example.movieworld.moviedata.service;

import com.example.movieworld.genre.Genre;
import com.example.movieworld.genre.GenreRepository;
import com.example.movieworld.MovieGenre;
import com.example.movieworld.movie.domain.Movie;
import com.example.movieworld.moviedata.dto.GenreInputDto;
import com.example.movieworld.moviedata.dto.MovieDataDto;
import com.example.movieworld.moviedata.repository.MovieDataRepository;
import com.example.movieworld.moviedata.dto.MovieInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieDataService {
    @Autowired
    private MovieDataRepository movieDataRepository;
    @Autowired
    private GenreRepository genreRepository;

    public void inputKoreanMovieData(){
        HttpHeaders headers =new HttpHeaders();

        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzZDQxYmY0NGY4ZWZmMjhjY2FlMDYxYjQzODA3MTMxZCIsIm5iZiI6MTc1MzE0OTI1MS4xMTYsInN1YiI6IjY4N2VlZjQzNmEzODA0YzIwZTE2YTc2ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.HoJBVwKQXsv8WcGlM1OnfmM2NpQOPJ3YqMZX7CEbIoY";
        headers.set("Authorization",token);
        headers.set("Accept","application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String url ="https://api.themoviedb.org/3/discover/movie?api_key=3d41bf44f8eff28ccae061b43807131d&language=ko&page=";

        ResponseEntity<MovieInputDto> results;
        List<Movie> movieList = new ArrayList<>();
        for(int i=1; i<=100; i++){
            results = restTemplate.getForEntity(url+i, MovieInputDto.class,entity);
            MovieInputDto result = results.getBody();
            List<MovieDataDto> movieDataDtoResult = result.getResults();

            for(int j = 0; j< movieDataDtoResult.size(); j++){
                Movie movie = new Movie(movieDataDtoResult.get(j));
                for(int k = 0 ; k<movieDataDtoResult.get(j).getGenre_ids().size(); k++){
                    Optional<Genre> genre = genreRepository.findById((long)movieDataDtoResult.get(j).getGenre_ids().get(k));
                    MovieGenre movieGenre =new MovieGenre(movie,genre.get());

                    movie.addMovieGenre(movieGenre);
                    genre.get().addMovieGenre(movieGenre);
                }
                movieList.add(movie);
            }
        }
        movieDataRepository.saveAll(movieList);
    }

    public void inputGenreData(){
        HttpHeaders headers =new HttpHeaders();

        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzZDQxYmY0NGY4ZWZmMjhjY2FlMDYxYjQzODA3MTMxZCIsIm5iZiI6MTc1MzE0OTI1MS4xMTYsInN1YiI6IjY4N2VlZjQzNmEzODA0YzIwZTE2YTc2ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.HoJBVwKQXsv8WcGlM1OnfmM2NpQOPJ3YqMZX7CEbIoY";
        headers.set("Authorization",token);
        headers.set("Accept","application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String url ="https://api.themoviedb.org/3/genre/movie/list?api_key=3d41bf44f8eff28ccae061b43807131d&language=ko";

        ResponseEntity<GenreInputDto> results = restTemplate.getForEntity(url, GenreInputDto.class,entity);
        GenreInputDto genreInputDto = results.getBody();

        List<Genre> genreList = new ArrayList<>();
        for(int i = 0 ; i<genreInputDto.getGenres().size(); i++){
            Genre genre = new Genre().builder()
                    .genreId(genreInputDto.getGenres().get(i).getId())
                    .genreName(genreInputDto.getGenres().get(i).getName())
                    .build();
            genreList.add(genre);
        }
        genreRepository.saveAll(genreList);
    }
}
