package com.example.movieworld.movie.dto;

import com.example.movieworld.genre.GenreResDto;
import lombok.AllArgsConstructor;
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
}
