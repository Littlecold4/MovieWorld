package com.example.movieworld.movie.repository;

import com.example.movieworld.TestUtils;
import com.example.movieworld.movie.dto.MovieListResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@DisplayName("MovieRepository 테스트")
@Import(TestUtils.class)
public class getMovieListRepository {
    @Autowired
    private TestUtils testUtils;
    @Autowired
    private MovieRepository movieRepository;

    int pageNum;

    @BeforeEach
    void setup(){
        testUtils.addMockGenre(5);

        List<Long> genreIdList = new ArrayList<>(Arrays.asList(0L,4L));
        testUtils.addMockMovie(6,genreIdList);

        genreIdList = new ArrayList<>(Arrays.asList(1L,3L));
        testUtils.addMockMovie(15,genreIdList);


    }
    @Nested
    @DisplayName("Repository _ Movie 리스트 조회 _ 성공")
    public class Success{
        @Test
        @DisplayName("Success _ getMovieList")
        void success_getMovieList(){

            pageNum = 0;
            Page<MovieListResDto> page1Result = movieRepository.getMovieList(pageNum);
            pageNum=1;
            Page<MovieListResDto> page2Result = movieRepository.getMovieList(pageNum);

            assertEquals(page1Result.getContent().get(0).getTitle(), "TEST_title_0");
            assertEquals(page1Result.getContent().get(0).getGenres().get(0).getGenreName(), "TEST_genre_0");
            assertEquals(page1Result.getContent().get(0).getGenres().get(1).getGenreName(), "TEST_genre_4");
            assertEquals(page1Result.getContent().get(1).getTitle(), "TEST_title_1");

            assertEquals(page2Result.getContent().get(0).getTitle(), "TEST_title_4");
            assertEquals(page2Result.getContent().get(0).getGenres().get(0).getGenreName(), "TEST_genre_1");
        }
    }
}
