package com.example.movieworld.movie.controller;

import com.example.movieworld.jwt.JwtAuthenticationFilter;
import com.example.movieworld.movie.dto.MovieListResDto;
import com.example.movieworld.movie.service.MovieService;
import com.example.movieworld.security.WithMockCustomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(MovieController.class)
public class getMovieListControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private MovieService movieService;
    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    Page<MovieListResDto> expectedResult;

    @BeforeEach
    void setup(){
        when(movieService.getMovieList(anyInt())).thenReturn(expectedResult);
    }

    @Test
    @DisplayName("Controller _ Movie 리스트 조회 성공")
    @WithMockCustomUser
    void success_getMovieList() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNum","0"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}
