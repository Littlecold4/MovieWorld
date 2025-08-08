package com.example.movieworld.movie.controller;

import com.example.movieworld.movie.dto.MovieListResDto;
import com.example.movieworld.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("")
    public ResponseEntity<Page<MovieListResDto>> getMovieList(@RequestParam int pageNum) {
        return new ResponseEntity<>(movieService.getMovieList(pageNum),HttpStatus.OK);
    }

}
