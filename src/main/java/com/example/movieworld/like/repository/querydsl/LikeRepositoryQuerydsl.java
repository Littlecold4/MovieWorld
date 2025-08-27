package com.example.movieworld.like.repository.querydsl;

import com.example.movieworld.movie.dto.MovieListResDto;
import com.example.movieworld.user.domain.User;
import org.springframework.data.domain.Page;

public interface LikeRepositoryQuerydsl {
    Page<MovieListResDto> getLikeMovieList(int pageNum, User user);
}
