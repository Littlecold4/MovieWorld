package com.example.movieworld.movie.service;

import com.example.movieworld.user.UserRepository;
import com.example.movieworld.common.CustomException;
import com.example.movieworld.common.ErrorCode;
import com.example.movieworld.movie.dto.MovieDetailResDto;
import com.example.movieworld.movie.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class getMovieDetailServiceTest {
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private MovieService movieService;

    MovieDetailResDto expectedResult ;

    @BeforeEach
    void setup(){
        expectedResult = new MovieDetailResDto();
    }

    @Nested
    @DisplayName("Service _ Movie 상세 조회 _ 성공")
    public class Success{

        @Test
        @DisplayName("성공")
        void success_getMovieDetail(){
            when(movieRepository.getMovieDetail(anyLong(),anyLong())).thenReturn(expectedResult);
            when(movieRepository.existsById(1L)).thenReturn(true);
            when(userRepository.existsById(1L)).thenReturn(true);

            MovieDetailResDto actualResult = movieService.getMovieDetail(1L,1L);

            assertNotNull(actualResult);
            assertEquals(expectedResult,actualResult);

            verify(movieRepository,times(1)).getMovieDetail(1L,1L);
            verify(movieRepository,times(1)).existsById(1L);
            verify(userRepository,times(1)).existsById(1L);


        }
    }

    @Nested
    @DisplayName("Failure _ Movie 상세 조회 _ 실패")
    public class Failure{
        @Test
        @DisplayName("실패 _ 해당 영화가 삭제된 경우")
        void fail_Movie_Not_Exist(){
            Long invalidMovieId = -1L;
            when(movieRepository.existsById(invalidMovieId)).thenReturn(false);



            Exception ex = assertThrows(CustomException.class,
                    ()-> movieService.getMovieDetail(invalidMovieId,1L));
            assertEquals(ErrorCode.MOVIE_NOT_EXIST.getMessage(),
                    ex.getMessage());

            verify(movieRepository,never()).getMovieDetail(anyLong(),anyLong());
            verify(movieRepository,times(1)).existsById(invalidMovieId);
        }

        @Test
        @DisplayName("실패 _ 해당 유저가 존재하지 않을 경우")
        void fail_User_Not_Exist(){
            Long invalidUserId = -1L;
            when(userRepository.existsById(invalidUserId)).thenReturn(false);
            when(movieRepository.existsById(1L)).thenReturn(true);


            Exception ex = assertThrows(CustomException.class,
                    ()->movieService.getMovieDetail(1L,invalidUserId));
            assertEquals(ErrorCode.USER_NOT_EXIST.getMessage(),
                    ex.getMessage());

            verify(movieRepository,never()).getMovieDetail(anyLong(),anyLong());
            verify(userRepository,times(1)).existsById(invalidUserId);
            verify(movieRepository,times(1)).existsById(1L);


        }
    }
}
