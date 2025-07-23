package com.example.movieworld.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MovieInputDto {
    private int page;
    private List<MovieDataDto> results;

}
