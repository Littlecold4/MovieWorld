package com.example.movieworld.movie.service;

import com.example.movieworld.common.CustomException;
import com.example.movieworld.common.ErrorCode;
import com.example.movieworld.movie.dto.MovieResDto;
import com.example.movieworld.movie.repository.MovieRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@NoArgsConstructor
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    public Page<MovieResDto> getMovieList(int pageNum){
        if(pageNum<0) throw new CustomException(ErrorCode.INVALID_PAGE_NUMBER);
        return movieRepository.getMovieList(pageNum);
    }
}
