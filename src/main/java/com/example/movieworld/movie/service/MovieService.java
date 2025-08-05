package com.example.movieworld.movie.service;

import com.example.movieworld.movie.dto.MovieResDto;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@NoArgsConstructor
public class MovieService {

    public Page<MovieResDto> getMovieList(Pageable pageable){
        return null;
    }
}
