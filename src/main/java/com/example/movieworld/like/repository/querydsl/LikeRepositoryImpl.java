package com.example.movieworld.like.repository.querydsl;

import com.example.movieworld.genre.GenreResDto;
import com.example.movieworld.movie.dto.MovieListResDto;
import com.example.movieworld.user.domain.User;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.movieworld.movie.domain.QMovie.movie;
import static com.example.movieworld.QMovieGenre.movieGenre;
import static com.example.movieworld.like.domain.QLike.like;
import static com.querydsl.jpa.JPAExpressions.*;

public class LikeRepositoryImpl implements LikeRepositoryQuerydsl{
    private final JPAQueryFactory queryFactory;

    public LikeRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<MovieListResDto> getLikeMovieList(int pageNum, User user) {
        Pageable pageable = PageRequest.of(pageNum,10);
        QueryResults<MovieListResDto> results = queryFactory
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
                .join(movie.likes,like)
                .where(like.user.eq(user)
                        .and(like.movie.eq(movie)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MovieListResDto> movieList = results.getResults();

        for(int i = 0; i<movieList.size(); i++) {
            List<GenreResDto> genres = queryFactory
                    .select(Projections.constructor(
                            GenreResDto.class,
                            movieGenre.genre.genreId,
                            movieGenre.genre.genreName
                    )).from(movieGenre)
                    .where(movieGenre.movie.movieId.eq(movieList.get(i).getMovieId()))
                    .from(movieGenre)
                    .fetch();

            movieList.get(i).setGenres(genres);
        }
        return new PageImpl<>(movieList,pageable,results.getTotal());
    }
}
