package com.example.movieworld.movie.controller;

import com.example.movieworld.movie.dto.MovieDetailResDto;
import com.example.movieworld.movie.dto.MovieListBySearchResDto;
import com.example.movieworld.movie.dto.MovieListResDto;
import com.example.movieworld.movie.service.MovieService;
import com.example.movieworld.user.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("")
    public ResponseEntity<Page<MovieListResDto>> getMovieList(@RequestParam(defaultValue = "0") int pageNum,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(movieService.getMovieList(pageNum),HttpStatus.OK);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDetailResDto> getMovieDetail(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @PathVariable Long movieId){
        return new ResponseEntity<>(movieService.getMovieDetail(movieId,userDetails.getUser().getId()),HttpStatus.OK);
    }

    @GetMapping("/genre/{genreId}")
    public ResponseEntity<Page<MovieListResDto>> getMovieListByGenre(@RequestParam(defaultValue = "0") int pageNum,
                                                                     @PathVariable Long genreId,
                                                                     @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(movieService.getMovieListByGenre(genreId,pageNum),HttpStatus.OK);
    }

}
