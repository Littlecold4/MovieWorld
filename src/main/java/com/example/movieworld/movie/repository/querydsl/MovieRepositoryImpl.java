package com.example.movieworld.movie.repository.querydsl;

import com.example.movieworld.genre.GenreResDto;
import com.example.movieworld.movie.dto.MovieDetailResDto;
import com.example.movieworld.movie.dto.MovieListResDto;
import com.querydsl.core.QueryResults;
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
import static com.example.movieworld.like.domain.QLike.like;
import static com.querydsl.jpa.JPAExpressions.*;



public class MovieRepositoryImpl implements MovieRepositoryQuerydsl{
    private final JPAQueryFactory queryFactory;

    public MovieRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MovieListResDto> getMovieList(int pageNum) {
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MovieListResDto> movieList = results.getResults();

        for(int i = 0; i<movieList.size(); i++){
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

    @Override
    public Page<MovieListResDto> getMovieListByGenre(Long genreId, int pageNum) {
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
                .join(movie.Genres,movieGenre)
                .where(movieGenre.genre.genreId.eq(genreId)
                        .and(movieGenre.movie.eq(movie)))
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

    @Override
    public Page<MovieListResDto> getMovieListBySearch(String keyword, int pageNum) {
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
                .where(movie.title.contains(keyword))
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
                    .where(movieGenre.movie.title.contains(keyword))
                    .from(movieGenre)
                    .fetch();

            movieList.get(i).setGenres(genres);
        }
        return new PageImpl<>(movieList,pageable,results.getTotal());
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
