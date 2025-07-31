package com.example.movieworld.movie.repository.querydsl;

import com.example.movieworld.GenreResDto;
import com.example.movieworld.movie.dto.MovieResDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.movieworld.movie.domain.QMovie.movie;
import static com.example.movieworld.QMovieGenre.movieGenre;


public class MovieRepositoryImpl implements MovieRepositoryQuerydsl{
    private final JPAQueryFactory queryFactory;

    public MovieRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MovieResDto> getMovieList(Pageable pageable) {
        List<MovieResDto> results = queryFactory
                .select(Projections.constructor(
                        MovieResDto.class,
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
}
