package com.example.movieworld.movie.dto;

import com.example.movieworld.GenreResDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
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
}
