package com.example.movieworld;

import com.example.movieworld.movie.domain.Movie;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "TB_MOVIEGENRE")
public class MovieGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long movieGenreId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOVIE_ID",nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENRE_ID",nullable = false)
    private Genre genre;

    public MovieGenre(Movie movie,Genre genre){
        this.movie = movie;
        this.genre = genre;
    }

}
