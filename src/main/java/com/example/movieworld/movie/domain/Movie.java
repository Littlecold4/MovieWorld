package com.example.movieworld.movie.domain;

import com.example.movieworld.like.domain.Like;
import com.example.movieworld.MovieGenre;
import com.example.movieworld.moviedata.dto.MovieDataDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TB_MOVIE")
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MOVIE_ID")
    private Long movieId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "OVERVIEW",length = 1000)
    private String overview;

    @Column(name = "ADULT")
    private boolean adult;

    @Column(name = "RELEASE_DATE")
    private String releaseDate;

    @Column(name = "POSTER_PATH")
    private String posterPath;

    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<MovieGenre> Genres = new HashSet<>();

    // mappedBy 값을 Like 엔티티의 movie 필드 이름인 "movie"로 수정
    // @Column(name = "LIKE") 제거
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Like> likes = new ArrayList<>();

    public Movie(MovieDataDto movieDataDto){
        this.adult = movieDataDto.isAdult();
        this.overview =movieDataDto.getOverview();
        this.posterPath = movieDataDto.getPoster_path();
        this.releaseDate = movieDataDto.getRelease_date();
        this.title = movieDataDto.getTitle();
    }

    public void addMovieGenre(MovieGenre movieGenre){
        this.getGenres().add(movieGenre);
        movieGenre.setMovie(this);
    }


}
