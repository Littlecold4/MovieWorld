package com.example.movieworld.movie.repository.querydsl;

import com.example.movieworld.movie.dto.MovieResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieRepositoryQuerydsl {
    Page<MovieResDto> getMovieList(Pageable pageable);
}
