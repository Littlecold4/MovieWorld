package com.example.movieworld.like.repository.querydsl;

import com.example.movieworld.movie.dto.MovieListResDto;
import org.springframework.data.domain.Page;

public interface LikeRepositoryQuerydsl {
    Page<MovieListResDto> getLikeMovieList(int pageNum);
}
