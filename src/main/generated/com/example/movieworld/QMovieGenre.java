package com.example.movieworld;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMovieGenre is a Querydsl query type for MovieGenre
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMovieGenre extends EntityPathBase<MovieGenre> {

    private static final long serialVersionUID = -533277138L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMovieGenre movieGenre = new QMovieGenre("movieGenre");

    public final QGenre genre;

    public final com.example.movieworld.movie.QMovie movie;

    public final NumberPath<Long> movieGenreId = createNumber("movieGenreId", Long.class);

    public QMovieGenre(String variable) {
        this(MovieGenre.class, forVariable(variable), INITS);
    }

    public QMovieGenre(Path<? extends MovieGenre> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMovieGenre(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMovieGenre(PathMetadata metadata, PathInits inits) {
        this(MovieGenre.class, metadata, inits);
    }

    public QMovieGenre(Class<? extends MovieGenre> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.genre = inits.isInitialized("genre") ? new QGenre(forProperty("genre")) : null;
        this.movie = inits.isInitialized("movie") ? new com.example.movieworld.movie.QMovie(forProperty("movie")) : null;
    }

}

