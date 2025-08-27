package com.example.movieworld.like.service;

import com.example.movieworld.common.CustomException;
import com.example.movieworld.common.ErrorCode;
import com.example.movieworld.like.repository.LikeRepository;
import com.example.movieworld.movie.dto.MovieListResDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.example.movieworld.TestUtils.createMovieResDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GetLikedMovieServiceTest {
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private LikeService likeService;

    private Pageable pageable;
    Page<MovieListResDto> expectedResult;
    private User loginUser;

    @BeforeEach
    void setup(){
        loginUser = new User(1L,"TEST_userEmail");
    }

    @Nested
    @DisplayName("Service _ 좋아요 한 Movie 리스트 조회 _ 성공")
    public class Success{
        @Test
        @DisplayName("성공")
        void success_getLikedMovieList(){
            //given
            int pageNum = 0;
            long totalLikedMovie = 25L;
            pageable = PageRequest.of(pageNum,10);
            expectedResult = new PageImpl<>(createMovieResDto(10),pageable,totalLikedMovie);

            when(likeRepository.getLikeMovieList(0,loginUser)).thenReturn(expectedResult);
            when(userRepository.findById(loginUser.getId())).thenReturn(Optional.of(loginUser));

            //when
            Page<MovieListResDto> actualResult = likeService.getLikedMovieList(pageNum,loginUser.getId());

            //then
            assertNotNull(actualResult);
            assertEquals(expectedResult.getContent(),actualResult.getContent());
            assertEquals(expectedResult.getTotalElements(),actualResult.getTotalElements());

            verify(likeRepository,times(1)).getLikeMovieList(0,loginUser);
            verify(userRepository,times(1)).findById(loginUser.getId());
        }

        @Test
        @DisplayName("성공 - 너무 큰 페이지 넘버가 들어온 경우, 마지막 페이지 반환")
        void success_Exceed_Page_Number(){
            //given
            int exceedPageNum = Integer.MAX_VALUE;
            long totalLikedMovies = 25;
            int lastPageNum = 25/10;
            pageable = PageRequest.of(lastPageNum,10);

            expectedResult = new PageImpl<>(createMovieResDto(5),pageable,totalLikedMovies); // 마지막 페이지는 5개의 movie를 가짐
            when(movieRepository.count()).thenReturn(totalLikedMovies);
            when(likeRepository.getLikeMovieList(lastPageNum,loginUser)).thenReturn(expectedResult);
            when(userRepository.findById(loginUser.getId())).thenReturn(Optional.of(loginUser));

            //when
            Page<MovieListResDto> actualResult = likeService.getLikedMovieList(exceedPageNum,loginUser.getId());

            //then
            assertNotNull(actualResult);
            assertEquals(expectedResult.getContent(),actualResult.getContent());
            assertEquals(expectedResult.getTotalElements(),actualResult.getTotalElements());

            verify(movieRepository,times(1)).count();
            verify(userRepository,times(1)).findById(loginUser.getId());
            verify(likeRepository,times(1)).getLikeMovieList(lastPageNum,loginUser);
        }
    }

    @Nested
    @DisplayName("Service _ 좋아요한 Movie List 조회 _ 실패")
    public class Failure{
        @Test
        @DisplayName("실패 _ 페이지 넘버 오류")
        void fail_Invalid_Page_Number(){
            //given
            int invalidPageNum = -1;

            //when
            Exception ex = assertThrows(CustomException.class,
                    ()-> likeService.getLikedMovieList(invalidPageNum, loginUser.getId()));

            //then
            assertEquals(ErrorCode.INVALID_PAGE_NUMBER.getMessage(),
                    ex.getMessage());

            verify(likeRepository,never()).getLikeMovieList(anyInt(),any(User.class));
        }

        @Test
        @DisplayName("실패 _ 존재하지 않는 유저")
        void fail_User_Not_Exist(){
            //given
            int pageNum = 0;
            long invalidUserId = -1L;

            when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());

            //when
            Exception ex = assertThrows(CustomException.class,
                    ()->likeService.getLikedMovieList(pageNum,invalidUserId));

            //then
            assertEquals(ErrorCode.USER_NOT_EXIST.getMessage(),
                    ex.getMessage());
            verify(likeRepository,never()).getLikeMovieList(anyInt(),any(User.class));
            verify(userRepository,times(1)).findById(invalidUserId);
        }
    }
}
