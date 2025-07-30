package com.example.movieworld.movie;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMovie is a Querydsl query type for Movie
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMovie extends EntityPathBase<Movie> {

    private static final long serialVersionUID = -1757450153L;

    public static final QMovie movie = new QMovie("movie");

    public final BooleanPath adult = createBoolean("adult");

    public final SetPath<com.example.movieworld.MovieGenre, com.example.movieworld.QMovieGenre> Genres = this.<com.example.movieworld.MovieGenre, com.example.movieworld.QMovieGenre>createSet("Genres", com.example.movieworld.MovieGenre.class, com.example.movieworld.QMovieGenre.class, PathInits.DIRECT2);

    public final ListPath<com.example.movieworld.Like, com.example.movieworld.QLike> likes = this.<com.example.movieworld.Like, com.example.movieworld.QLike>createList("likes", com.example.movieworld.Like.class, com.example.movieworld.QLike.class, PathInits.DIRECT2);

    public final NumberPath<Long> movieId = createNumber("movieId", Long.class);

    public final StringPath overview = createString("overview");

    public final StringPath posterPath = createString("posterPath");

    public final StringPath releaseDate = createString("releaseDate");

    public final StringPath title = createString("title");

    public QMovie(String variable) {
        super(Movie.class, forVariable(variable));
    }

    public QMovie(Path<? extends Movie> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMovie(PathMetadata metadata) {
        super(Movie.class, metadata);
    }

}

