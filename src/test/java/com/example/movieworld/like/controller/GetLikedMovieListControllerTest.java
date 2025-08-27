package com.example.movieworld.like.controller;

import com.example.movieworld.jwt.JwtAuthenticationFilter;
import com.example.movieworld.jwt.TokenProvider;
import com.example.movieworld.like.service.LikeService;
import com.example.movieworld.movie.dto.MovieListResDto;
import com.example.movieworld.security.WithMockCustomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;


@WebMvcTest(controllers = LikeController.class)
public class GetLikedMovieListControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mvc;
    @MockitoBean
    private LikeService likeService;
    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockitoBean
    private TokenProvider tokenProvider;
    private Page<MovieListResDto> expectedResult ;


    @BeforeEach
    void setup() {
        expectedResult=null;
    }

    @Nested
    @DisplayName("Controller _ 좋아요한 Movie 리스트 조회 성공")
    public class Success{
        @Test
        @DisplayName("성공")
        @WithMockCustomUser
        void success_getLikedMovieList() throws Exception {
            when(likeService.getLikedMovieList(anyInt(),anyLong())).thenReturn(expectedResult);

            mvc.perform(MockMvcRequestBuilders.get("/like/movie")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("pageNum","0"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().is(200));
        }
    }
}
