package com.example.movieworld.like.repository;

import com.example.movieworld.TestUtils;
import com.example.movieworld.config.QuerydslConfig;
import com.example.movieworld.movie.dto.MovieListResDto;
import com.example.movieworld.user.domain.User;
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
@Import({TestUtils.class, QuerydslConfig.class})
public class getLikedMovieListRepositoryTest {
    @Autowired
    private TestUtils testUtils;
    @Autowired
    private LikeRepository likeRepository;
    private User loginUser;

    @BeforeEach
    void setup(){
        loginUser = testUtils.addMockUser();

        testUtils.addMockGenre(5);

        List<Long> genreIdList = new ArrayList<>(Arrays.asList(0L,4L));
        testUtils.addMockMovie(6,genreIdList);

        genreIdList = new ArrayList<>(Arrays.asList(1L,3L));
        testUtils.addMockMovie(15,genreIdList);

        testUtils.addMockLike(loginUser.getId(), 1L);
        testUtils.addMockLike(loginUser.getId(), 5L);
        testUtils.addMockLike(loginUser.getId(), 10L);
        testUtils.addMockLike(loginUser.getId(), 15L);
    }
    @Nested
    @DisplayName("Repository _ 내가 좋아요 한 Movie 리스트 조회 _ 성공")
    public class Success{
        @Test
        @DisplayName("Success _ getLikedMovieList")
        void success_getLikedMovieList(){
            int pageNum = 0;
            Page<MovieListResDto> results = likeRepository.getLikeMovieList(pageNum,loginUser);

            assertEquals(4,results.getTotalElements());
            assertEquals(1,results.getTotalPages());

            assertEquals(1L,results.getContent().get(0).getMovieId());
            assertEquals(5L,results.getContent().get(1).getMovieId());
            assertEquals(10L,results.getContent().get(2).getMovieId());
            assertEquals(15L,results.getContent().get(3).getMovieId());

            assertEquals(1,results.getContent().get(0).getLikeCnt());

        }
    }
}
