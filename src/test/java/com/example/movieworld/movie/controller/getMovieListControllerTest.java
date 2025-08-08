package com.example.movieworld.movie.controller;

import com.example.movieworld.movie.dto.MovieListResDto;
import com.example.movieworld.movie.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

@WebMvcTest(MovieController.class)
public class getMovieListControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mvc;

    @Mock
    private MovieService movieService;

    Page<MovieListResDto> expectedResult;

    @BeforeEach
    void setup(){
        when(movieService.getMovieList(anyInt())).thenReturn(expectedResult);
    }

    @Test
    @DisplayName("Controller _ Movie 리스트 조회 성공")
    void success_getMovieList() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNum","0"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}
