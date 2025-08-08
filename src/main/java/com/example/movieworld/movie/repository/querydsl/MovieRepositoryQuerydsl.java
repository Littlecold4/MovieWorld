package com.example.movieworld.movie.repository.querydsl;

import com.example.movieworld.movie.dto.MovieListResDto;
import org.springframework.data.domain.Page;

public interface MovieRepositoryQuerydsl {
    Page<MovieListResDto> getMovieList(int pageNum);
}
