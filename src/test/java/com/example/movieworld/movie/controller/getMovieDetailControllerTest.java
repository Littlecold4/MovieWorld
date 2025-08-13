package com.example.movieworld.movie.controller;

import com.example.movieworld.movie.dto.MovieDetailResDto;
import com.example.movieworld.movie.service.MovieService;
import com.example.movieworld.security.WithMockCustomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(MovieController.class)
public class getMovieDetailControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MovieService movieService;

    MovieDetailResDto expectedResult;

    @BeforeEach
    void setup(){
        expectedResult = new MovieDetailResDto().builder()
                .adult(false)
                .likeChk(false)
                .likeCnt(0)
                .overview("TEST_overview")
                .posterPath("TEST_posterPath")
                .releaseDate("TEST_releaseDate")
                .title("TEST_title")
                .build();

        when(movieService.getMovieDetail(anyLong(),anyLong())).thenReturn(expectedResult);
    }

    @Test
    @DisplayName("Controller _ Movie 상세 조회 성공")
    @WithMockCustomUser
    void success_getMovieDetail() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/movie/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.overview").value("TEST_overview"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("TEST_title"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.adult").value("true"));
    }
}
