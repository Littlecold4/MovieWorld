package com.example.movieworld;

import com.example.movieworld.movie.MovieRepository;
import com.example.movieworld.movie.MovieResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@DisplayName("MovieRepository 테스트")
@Import(TestUtils.class)
public class MovieRepositoryTest {
    @Autowired
    private TestUtils testUtils;
    @Autowired
    private MovieRepository movieRepository;

    Pageable pageable;

    @BeforeEach
    void setup(){
        testUtils.addMockMovie(22);
    }
    @Nested
    @DisplayName("Movie 리스트 조회 _ 성공")
    public class Success{
        void success_getMovieList(){

            pageable =PageRequest.of(0,10);
            Page<MovieResDto> page1Result = movieRepository.getMovieList(pageable);
            pageable =PageRequest.of(1,10);
            Page<MovieResDto> page2Result = movieRepository.getMovieList(pageable);

            assertEquals(page1Result.getContent().get(0).getTitle(),
                    "TEST_title_0");
            assertEquals(page1Result.getContent().get(1).getTitle(),
                    "TEST_title_1");
            assertEquals(page2Result.getContent().get(10).getTitle(),
                    "TEST_title_0");
        }
    }
    @Nested
    @DisplayName("Movie 리스트 조회  _ 실패")
    public class Fail{

        @Test
        @DisplayName("실패 _ 오버된 페이지")
        void fail_getMovieList_over_page(){
            pageable =PageRequest.of(4,10);

            Exception ex = assertThrows(Exception.class,
                    ()->movieRepository.getMovieList(pageable));
        }
    }
}
