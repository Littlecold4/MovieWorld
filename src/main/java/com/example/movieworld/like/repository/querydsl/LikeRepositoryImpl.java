package com.example.movieworld.like.repository.querydsl;

import com.example.movieworld.movie.dto.MovieListResDto;
import org.springframework.data.domain.Page;

public class LikeRepositoryImpl implements LikeRepositoryQuerydsl{
    @Override
    public Page<MovieListResDto> getLikeMovieList(int pageNum) {
        return null;
    }
}
