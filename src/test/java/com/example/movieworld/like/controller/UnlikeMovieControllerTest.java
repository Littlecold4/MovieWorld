package com.example.movieworld.like.controller;

import com.example.movieworld.common.CustomException;
import com.example.movieworld.common.ErrorCode;
import com.example.movieworld.config.WebSecurityConfig;
import com.example.movieworld.jwt.JwtAuthenticationFilter;
import com.example.movieworld.jwt.TokenProvider;
import com.example.movieworld.like.service.LikeService;
import com.example.movieworld.security.WithMockCustomUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = LikeController.class)
//excludeAutoConfiguration = SecurityAutoConfiguration.class,
//excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {OncePerRequestFilter.class}))
//@Import(WebSecurityConfig.class)
public class UnlikeMovieControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private LikeService likeService;
    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockitoBean
    private TokenProvider tokenProvider;

    @BeforeEach
    void setup() {
    }

    @Nested
    @DisplayName("Controller _ Movie 좋아요 취소 성공")
    public class Success{
        @Test
        @DisplayName("성공")
        @WithMockCustomUser
        void success_likeMovie() throws Exception{
            doNothing().when(likeService).unlikeMovie(anyLong(),anyLong());

            mvc.perform(MockMvcRequestBuilders.delete("/like/unlike/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is(200))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.content().string("좋아요 처리에 성공하였습니다."));
        }
    }

    @Nested
    @DisplayName("Controller _ Movie 좋아요 _ 실패")
    public class Failure{
        @Test
        @DisplayName("실패 _ 이미 좋아요된 영화")
        @WithMockCustomUser
        void fail_Moive_Already_Like() throws Exception {
            doThrow(new CustomException(ErrorCode.MOVIE_ALREADY_UNLIKED)).when(likeService)
                    .unlikeMovie(anyLong(),anyLong());

            mvc.perform(MockMvcRequestBuilders.delete("/like/unlike/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is(400))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.content().string("이미 좋아요를 누른 영화입니다."));


        }

        @Test
        @DisplayName("실패 _ 삭제된 영화")
        @WithMockCustomUser
        void fail_Movie_Not_Exist() throws Exception {
            doThrow(new CustomException(ErrorCode.MOVIE_NOT_EXIST)).when(likeService)
                    .unlikeMovie(anyLong(),anyLong());

            mvc.perform(MockMvcRequestBuilders.delete("/like/unlike/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is(400))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.content().string("존재하지 않는 영화정보입니다."));
        }
    }
}
