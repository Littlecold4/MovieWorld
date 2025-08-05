package com.example.movieworld.moviedata.controller;

import com.example.movieworld.moviedata.service.MovieDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieDataController {

    @Autowired
    private MovieDataService movieDataService;

    @GetMapping("/get/korean")
    public void getKoreanMovieData(){
        movieDataService.inputKoreanMovieData();
    }

    @GetMapping("/get/genre")
    public void getGenreData(){
        movieDataService.inputGenreData();
    }


}
