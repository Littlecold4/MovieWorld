package com.example.movieworld.like.service;

import com.example.movieworld.common.CustomException;
import com.example.movieworld.common.ErrorCode;
import com.example.movieworld.like.domain.Like;
import com.example.movieworld.like.repository.LikeRepository;
import com.example.movieworld.movie.domain.Movie;
import com.example.movieworld.movie.repository.MovieRepository;
import com.example.movieworld.user.domain.User;
import com.example.movieworld.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    public void likeMovie(Long movieId,Long userId){
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                ()-> new CustomException(ErrorCode.MOVIE_NOT_EXIST)
        );
        User user = userRepository.findById(userId).orElseThrow(
                ()->new CustomException(ErrorCode.USER_NOT_EXIST)
        );

        if(likeRepository.existsByUserAndMovie(user,movie))
            throw new CustomException(ErrorCode.MOVIE_ALREADY_LIKED);

        Like like = new Like();
        movie.getLikes().add(like);
        user.getLikes().add(like);

        likeRepository.save(like);
    }

    public void unlikeMovie(Long movieId,Long userId){
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                ()-> new CustomException(ErrorCode.MOVIE_NOT_EXIST)
        );
        User user = userRepository.findById(userId).orElseThrow(
                ()->new CustomException(ErrorCode.USER_NOT_EXIST)
        );

        Like like = likeRepository.findByUserAndMovie(user,movie).orElseThrow(
                ()-> new CustomException(ErrorCode.MOVIE_ALREADY_UNLIKED)
        );
        likeRepository.delete(like);
    }
}
