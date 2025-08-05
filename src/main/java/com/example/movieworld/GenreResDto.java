package com.example.movieworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenreResDto {
    private Long genreId;
    private String genreName;
}
