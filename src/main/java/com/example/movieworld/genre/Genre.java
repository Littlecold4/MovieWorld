package com.example.movieworld.genre;

import com.example.movieworld.MovieGenre;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TB_GENRE")
@Getter
@NoArgsConstructor
public class Genre {
    @Id
    @Column(name = "GENRE_ID")
    private Long genreId;

    @Column(name = "GENRE_NAME")
    private String genreName;


    @OneToMany(mappedBy = "genre",cascade = CascadeType.ALL,orphanRemoval = true)
    private final Set<MovieGenre> movies = new HashSet<>();

    public void addMovieGenre(MovieGenre movieGenre){
        this.movies.add(movieGenre);
        movieGenre.setGenre(this);
    }
    @Builder
    public Genre(Long genreId,String genreName){
        this.genreId = genreId;
        this.genreName = genreName;
    }

}
