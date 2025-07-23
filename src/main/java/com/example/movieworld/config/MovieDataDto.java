package com.example.movieworld.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MovieDataDto {
    private boolean adult;
    private List<Integer> genre_ids;
    private String original_language;
    private String overview;
    private String poster_path;
    private String release_date;
    private String title;
}
