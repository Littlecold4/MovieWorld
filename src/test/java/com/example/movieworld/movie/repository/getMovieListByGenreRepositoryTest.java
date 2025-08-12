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
@DisplayName("getMovieListByGenreRepository 테스트")
@Import(TestUtils.class)
public class getMovieListByGenreRepositoryTest {
    @Autowired
    private TestUtils testUtils;
    @Autowired
    private MovieRepository movieRepository;
    int pageNum;
    Long genreId;

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
    @DisplayName("Repository _ Genre별 Movie 리스트 조회 _ 성공")
    public class Success{
        @Test
        @DisplayName("Success _ getMoviesByGenre")
        void success_getMoviesByGenre(){
            pageNum = 0;
            genreId=0L;
            Page<MovieListResDto> genre0Result = movieRepository.getMovieListByGenre(genreId,pageNum);

            pageNum = 1;
            genreId = 4L;
            Page<MovieListResDto> genre4Result = movieRepository.getMovieListByGenre(genreId,pageNum);

            assertEquals(16,genre0Result.getTotalElements());
            assertEquals(15,genre4Result.getTotalElements());

            assertEquals("TEST_title_0", genre0Result.getContent().get(0).getTitle());
            assertEquals("TEST_title_10", genre4Result.getContent().get(0).getTitle());

            assertEquals(10,genre0Result.getSize());
            assertEquals(10,genre4Result.getSize());

            assertEquals(true,genre4Result.isLast());
        }
    }

}
