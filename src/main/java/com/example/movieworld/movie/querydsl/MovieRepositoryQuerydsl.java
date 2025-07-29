package com.example.movieworld.movie.querydsl;

import com.example.movieworld.movie.MovieResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieRepositoryQuerydsl {
    Page<MovieResDto> getMovieList(Pageable pageable);
}
