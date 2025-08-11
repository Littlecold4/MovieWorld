package com.example.movieworld.movie.repository;

import com.example.movieworld.TestUtils;
import com.example.movieworld.User;
import com.example.movieworld.movie.dto.MovieDetailResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@DisplayName("MovieRepository 테스트")
@Import(TestUtils.class)
public class getMovieDetailRepository {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TestUtils testUtils;
    private User user;
    private List<Long> genreIdList;

    @BeforeEach
    void setup(){
        testUtils.addMockGenre(5);

        genreIdList = new ArrayList<>(Arrays.asList(0L,4L));
        testUtils.addMockMovie(6,genreIdList);

        user = new User().builder()
                .userEmail("TEST_userEmail_0")
                .userName("TEST_userName_0")
                .password("TEST_password_0")
                .build();
    }

    @Nested
    @DisplayName("Repository _ Movie 상세 조회")
    class Success{
        @Test
        @DisplayName("Success _ getMovieDetail")
        void success_getMovieDetail(){
            MovieDetailResDto movieDetailResDto =
                    movieRepository.getMovieDetail(0L);

            assertEquals("TEST_title_0",movieDetailResDto.getTitle());
            assertEquals(false,movieDetailResDto.isLikeChk());
            assertEquals(genreIdList,movieDetailResDto.getGenres());
        }
    }
}
