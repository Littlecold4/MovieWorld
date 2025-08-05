package com.example.movieworld.moviedata.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GenreInputDto {
    private List<GenreDataDto> genres;
}
