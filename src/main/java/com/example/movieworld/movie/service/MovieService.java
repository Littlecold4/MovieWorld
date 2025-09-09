package com.example.movieworld.movie.service;

import com.example.movieworld.movie.dto.MovieListBySearchResDto;
import com.example.movieworld.user.repository.UserRepository;
import com.example.movieworld.common.CustomException;
import com.example.movieworld.common.ErrorCode;
import com.example.movieworld.genre.GenreRepository;
import com.example.movieworld.movie.dto.MovieDetailResDto;
import com.example.movieworld.movie.dto.MovieListResDto;
import com.example.movieworld.movie.repository.MovieRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
@NoArgsConstructor
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GenreRepository genreRepository;

    public Page<MovieListResDto> getMovieList(int pageNum){
        if(pageNum<0) throw new CustomException(ErrorCode.INVALID_PAGE_NUMBER);
        long totalMovies = movieRepository.count();
        if(pageNum > totalMovies/10) pageNum = (int) totalMovies/10;
        return movieRepository.getMovieList(pageNum);
    }

    public MovieDetailResDto getMovieDetail(Long movieId, Long userId){
        if(!movieRepository.existsById(movieId)) throw new CustomException(ErrorCode.MOVIE_NOT_EXIST);
        if(!userRepository.existsById(userId)) throw new CustomException(ErrorCode.USER_NOT_EXIST);
        return movieRepository.getMovieDetail(movieId,userId);
    }

    public Page<MovieListResDto> getMovieListByGenre(Long genreId, int pageNum){
        if(!genreRepository.existsById(genreId)) throw new CustomException(ErrorCode.INVALID_GENRE_ID);
        if(pageNum<0) throw new CustomException(ErrorCode.INVALID_PAGE_NUMBER);
        long totalMovies = movieRepository.count();
        if(pageNum > totalMovies/10) pageNum = (int) totalMovies/10;
        return movieRepository.getMovieListByGenre(genreId,pageNum);
    }

    public MovieListBySearchResDto getMovieListBySearch(String keyword,int pageNum){
        return null;
    }
}
