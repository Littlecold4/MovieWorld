package com.example.movieworld.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MovieListBySearchResDto {
    Page<MovieListResDto> movieListResDto;
    String keyword;
}
