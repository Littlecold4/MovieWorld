package com.example.movieworld;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGenre is a Querydsl query type for Genre
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGenre extends EntityPathBase<Genre> {

    private static final long serialVersionUID = 672266920L;

    public static final QGenre genre = new QGenre("genre");

    public final NumberPath<Long> genreId = createNumber("genreId", Long.class);

    public final StringPath genreName = createString("genreName");

    public final SetPath<MovieGenre, QMovieGenre> movies = this.<MovieGenre, QMovieGenre>createSet("movies", MovieGenre.class, QMovieGenre.class, PathInits.DIRECT2);

    public QGenre(String variable) {
        super(Genre.class, forVariable(variable));
    }

    public QGenre(Path<? extends Genre> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGenre(PathMetadata metadata) {
        super(Genre.class, metadata);
    }

}

