package com.example.movieworld.movie.querydsl;

import com.example.movieworld.movie.MovieResDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public class MovieRepositoryImpl implements MovieRepositoryQuerydsl{
    private final JPAQueryFactory queryFactory;

    public MovieRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MovieResDto> getMovieList(Pageable pageable) {
        return null;
    }
}
