package com.example.movieworld.movie.dto;

import com.example.movieworld.GenreResDto;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MovieResDto {
    private Long movieId;
    private String title;
    private String overview;
    private boolean adult;
    private String releaseDate;
    private String posterPath;
    private List<GenreResDto> genres;
    private int likeCnt;

    public MovieResDto(Long movieId, String title, String overview, boolean adult, String releaseDate, String posterPath,int likeCnt) {
        this.movieId = movieId;
        this.title = title;
        this.overview = overview;
        this.adult = adult;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.likeCnt = likeCnt;
    }

    public void setGenres(List<GenreResDto> genres) {
        this.genres = genres;
    }

    public MovieResDto(Long movieId, String title) {
        this.movieId = movieId;
        this.title = title;
    }
}
