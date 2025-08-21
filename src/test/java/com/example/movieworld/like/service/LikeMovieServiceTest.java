package com.example.movieworld.like.service;

import com.example.movieworld.common.CustomException;
import com.example.movieworld.common.ErrorCode;
import com.example.movieworld.like.domain.Like;
import com.example.movieworld.like.repository.LikeRepository;
import com.example.movieworld.movie.domain.Movie;
import com.example.movieworld.movie.repository.MovieRepository;
import com.example.movieworld.user.domain.User;
import com.example.movieworld.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LikeMovieServiceTest {
    @Mock
    private LikeRepository likeRepository;
    @Mock private MovieRepository movieRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks
    private LikeService likeService;
    private Movie movie;
    private User loginUser;
    private Long movieId;
    private Long userId;
    @BeforeEach
    void setup(){
        movieId=1L;
        userId = 2L;
        movie =new Movie(movieId,"TEST_title_1");
        loginUser = new User(userId,"TEST_userName_1");
    }
    @Nested
    @DisplayName("Service _ Movie 좋아요 _ 성공")
    public class Success{
        @Test
        @DisplayName("성공")
        void success_likeMovie(){
            when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
            when(userRepository.findById(userId)).thenReturn(Optional.of(loginUser));
            when(likeRepository.existsByUserAndMovie(loginUser,movie)).thenReturn(false);
            when(likeRepository.save(any(Like.class))).thenReturn(new Like());

            likeService.likeMovie(movieId,userId);

            verify(movieRepository,times(1)).findById(movieId);
            verify(userRepository,times(1)).findById(userId);
            verify(likeRepository,times(1)).existsByUserAndMovie(loginUser,movie);
            verify(likeRepository,times(1)).save(any(Like.class));
        }
    }
    @Nested
    @DisplayName("Service _ Movie 좋아요 _ 실패")
    public class Failure{
        @Test
        @DisplayName("실패 _ 해당 영화가 존재하지 않는 경우")
        void fail_Movie_Not_Exist(){
            when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

            Exception ex = assertThrows(CustomException.class,
                    ()-> likeService.likeMovie(movieId,userId));
            assertEquals(ErrorCode.MOVIE_NOT_EXIST.getMessage(),
                    ex.getMessage());

            verify(movieRepository,times(1)).findById(movieId);
            verify(userRepository,never()).findById(userId);
            verify(likeRepository,never()).save(any(Like.class));
        }

        @Test
        @DisplayName("실패 _ 해당 유저가 존재하지 않는 경우")
        void fail_User_Not_Exist(){
            when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
            when(userRepository.findById(userId)).thenReturn(Optional.empty());

            Exception ex = assertThrows(CustomException.class,
                    ()-> likeService.likeMovie(movieId,userId));
            assertEquals(ErrorCode.USER_NOT_EXIST.getMessage(),
                    ex.getMessage());

            verify(movieRepository,times(1)).findById(movieId);
            verify(userRepository,times(1)).findById(userId);
            verify(likeRepository,never()).existsByUserAndMovie(loginUser,movie);
            verify(likeRepository,never()).save(any(Like.class));
        }

        @Test
        @DisplayName("실패 _ 이미 좋아요가 눌러져 있는 경우")
        void fail_Movie_Already_Liked(){
            when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
            when(userRepository.findById(userId)).thenReturn(Optional.of(loginUser));
            when(likeRepository.existsByUserAndMovie(loginUser,movie)).thenReturn(true);


            Exception ex = assertThrows(CustomException.class,
                    ()-> likeService.likeMovie(movieId,userId));
            assertEquals(ErrorCode.MOVIE_ALREADY_LIKED.getMessage(),
                    ex.getMessage());

            verify(movieRepository,times(1)).findById(movieId);
            verify(userRepository,times(1)).findById(userId);
            verify(likeRepository,times(1)).existsByUserAndMovie(loginUser,movie);
            verify(likeRepository,never()).save(any(Like.class));
        }
    }
}
