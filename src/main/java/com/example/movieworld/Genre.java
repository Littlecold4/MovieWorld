package com.example.movieworld;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TB_GENRE")
@Data
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GENRE_ID")
    private Long genreId;

    @Column(name = "GENRE_NAME")
    private String genreName;


    @OneToMany(mappedBy = "genre",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<MovieGenre> movies = new HashSet<>();

    public void addMovieGenre(MovieGenre movieGenre){
        this.movies.add(movieGenre);
        movieGenre.setGenre(this);
    }

}
