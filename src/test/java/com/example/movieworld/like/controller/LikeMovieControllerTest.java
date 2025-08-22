package com.example.movieworld.like.controller;

import com.example.movieworld.jwt.TokenProvider;
import com.example.movieworld.like.service.LikeService;
import com.example.movieworld.security.WithMockCustomUser;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;

@WebMvcTest(LikeController.class)
@Import(SecurityConfig.class)
public class LikeMovieControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mvc;

    @MockBean
    private LikeService likeService;
    @MockBean
    private TokenProvider tokenProvider;
    @BeforeEach
    void setup(){
        doNothing().when(likeService).likeMovie(anyLong(),anyLong());
    }

    @Nested
    @DisplayName("Controller _ Movie 좋아요 성공")
    public class Success{
        @Test
        @DisplayName("성공")
        @WithMockCustomUser
        void success_likeMovie() throws Exception{
            mvc.perform(MockMvcRequestBuilders.post("/like/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is(200))
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
            mvc.perform(MockMvcRequestBuilders.post("/like/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is(400))
                    .andExpect(MockMvcResultMatchers.content().string("이미 좋아요를 누른 영화입니다."));
        }

        @Test
        @DisplayName("실패 _ 삭제된 영화")
        @WithMockCustomUser
        void fail_Movie_Not_Exist() throws Exception {
            mvc.perform(MockMvcRequestBuilders.post("/like/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is(400))
                    .andExpect(MockMvcResultMatchers.content().string("존재하지 않는 영화정보입니다."));
        }
    }

}
