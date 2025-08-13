package com.example.movieworld.movie.dto;

import com.example.movieworld.genre.GenreResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailResDto {
    private Long movieId;
    private String title;
    private String overview;
    private boolean adult;
    private String releaseDate;
    private String posterPath;
    private List<GenreResDto> genres;
    private int likeCnt;
    private boolean likeChk;

    @Builder
    public MovieDetailResDto(Long movieId, String title, String overview, boolean adult, String releaseDate, String posterPath, int likeCnt, boolean likeChk) {
        this.movieId = movieId;
        this.title = title;
        this.overview = overview;
        this.adult = adult;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.likeCnt = likeCnt;
        this.likeChk = likeChk;
    }
    public void setGenres(List<GenreResDto> genres) {
        this.genres = genres;
    }

}
