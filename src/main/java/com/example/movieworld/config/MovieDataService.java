package com.example.movieworld.config;

import com.example.movieworld.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MovieDataService {
    @Autowired
    private MovieDataRepository movieDataRepository;

    public void inputKoreanMovieData(){
        HttpHeaders headers =new HttpHeaders();

        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzZDQxYmY0NGY4ZWZmMjhjY2FlMDYxYjQzODA3MTMxZCIsIm5iZiI6MTc1MzE0OTI1MS4xMTYsInN1YiI6IjY4N2VlZjQzNmEzODA0YzIwZTE2YTc2ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.HoJBVwKQXsv8WcGlM1OnfmM2NpQOPJ3YqMZX7CEbIoY";
        headers.set("Authorization",token);
        headers.set("Accept","application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String usl ="https://api.themoviedb.org/3/discover/movie?api_key=3d41bf44f8eff28ccae061b43807131d&language=ko&page=";
        String url1 = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=ko&page=1&sort_by=popularity.desc&with_release_type=2|3";

        ResponseEntity<MovieInputDto> results;

        for(int i=1; i<=100; i++){
            results = restTemplate.getForEntity(usl+i, MovieInputDto.class,entity);
            MovieInputDto result = results.getBody();
            List<MovieDataDto> movieDataDtoResult = result.getResults();

            for(int j = 0; j< movieDataDtoResult.size(); j++){
                movieDataRepository.save(
                        new Movie(movieDataDtoResult.get(j))
                );
            }
        }
    }
}
