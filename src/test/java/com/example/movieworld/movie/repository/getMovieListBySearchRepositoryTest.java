package com.example.movieworld.movie.repository;

import com.example.movieworld.TestUtils;
import com.example.movieworld.config.QuerydslConfig;
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
@DisplayName("getMovieListBySearchRepository 테스트")
@Import({TestUtils.class, QuerydslConfig.class})
public class getMovieListBySearchRepositoryTest {
    @Autowired
    private TestUtils testUtils;
    @Autowired
    private MovieRepository movieRepository;
    int pageNum;

    @BeforeEach
    void setup(){
        testUtils.addMockGenre(10);

        List<Long> genreIdList = new ArrayList<>(Arrays.asList(0L,4L));
        testUtils.addMockMovie(7,genreIdList);
        genreIdList = new ArrayList<>(Arrays.asList(2L,4L));
        testUtils.addMockMovie(8,genreIdList);
        genreIdList = new ArrayList<>(Arrays.asList(0L,3L));
        testUtils.addMockMovie(9,genreIdList);
    }
    @Nested
    @DisplayName("Repository _ 검색하여 Movie 리스트 조회 _ 성공")
    public class Success{
        @Test
        @DisplayName("Success _ getMovieListBySearch")
        void success_getMovieListBySearch(){
            pageNum = 2;
            String keyword1 = "TEST";
            Page<MovieListResDto> result_keyword1 = movieRepository.getMovieListBySearch(keyword1,pageNum);

            pageNum = 0;
            String keyword2 = "1";
            Page<MovieListResDto> result_keyword2 = movieRepository.getMovieListBySearch(keyword2,pageNum);

            assertEquals(24,result_keyword1.getTotalElements()); //7+8+9
            assertEquals(12,result_keyword2.getTotalElements()); // 1,10~19,21

            assertEquals(4,result_keyword1.getSize());
            assertEquals(10,result_keyword2.getSize());

            assertEquals("TEST_title_21",result_keyword1.getContent().get(0).getTitle());
            assertEquals("TEST_title_18",result_keyword2.getContent().get(9).getTitle());




        }
    }
}
