package com.example.movieworld.movie.repository.querydsl;

import com.example.movieworld.genre.GenreResDto;
import com.example.movieworld.movie.dto.MovieDetailResDto;
import com.example.movieworld.movie.dto.MovieListResDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.movieworld.movie.domain.QMovie.movie;
import static com.example.movieworld.QMovieGenre.movieGenre;
import static com.example.movieworld.QLike.like;
import static com.querydsl.jpa.JPAExpressions.*;



public class MovieRepositoryImpl implements MovieRepositoryQuerydsl{
    private final JPAQueryFactory queryFactory;

    public MovieRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MovieListResDto> getMovieList(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum,10);
        List<MovieListResDto> results = queryFactory
                .select(Projections.constructor(
                        MovieListResDto.class,
                        movie.movieId,
                        movie.title,
                        movie.overview,
                        movie.adult,
                        movie.releaseDate,
                        movie.posterPath,
                        movie.likes.size()
                )).from(movie)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        for(int i = 0; i<results.size(); i++){
            List<GenreResDto> genres = queryFactory
                    .select(Projections.constructor(
                            GenreResDto.class,
                            movieGenre.genre.genreId,
                            movieGenre.genre.genreName
                    )).from(movieGenre)
                    .where(movieGenre.movie.movieId.eq(results.get(i).getMovieId()))
                    .from(movieGenre)
                    .fetch();

            results.get(i).setGenres(genres);
        }


        return new PageImpl<>(results,pageable,results.size());
    }

    @Override
    public MovieDetailResDto getMovieDetail(Long movieId,Long userId) {
        MovieDetailResDto movieDetailResDto =queryFactory.
                select(Projections.constructor(
                        MovieDetailResDto.class,
                        movie.movieId,
                        movie.title,
                        movie.overview,
                        movie.adult,
                        movie.releaseDate,
                        movie.posterPath,
                        movie.likes.size(),
                        Expressions.as(
                               select(like)
                                       .from(like)
                                       .where(like.movie.movieId.eq(movieId)
                                               .and(like.user.id.eq(userId)))
                                       .exists(),
                                "likeChk"
                        )
                ))
                .from(movie)
                .where(movie.movieId.eq(movieId))
                .fetchOne();

        List<GenreResDto> genres = queryFactory
                .select(Projections.constructor(
                        GenreResDto.class,
                        movieGenre.genre.genreId,
                        movieGenre.genre.genreName
                )).from(movieGenre)
                .where(movieGenre.movie.movieId.eq(movieId))
                .from(movieGenre)
                .fetch();

        movieDetailResDto.setGenres(genres);

        return movieDetailResDto;
    }
}
