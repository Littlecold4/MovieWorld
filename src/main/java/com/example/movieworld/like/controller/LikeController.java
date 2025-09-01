package com.example.movieworld.like.controller;

import com.example.movieworld.like.service.LikeService;
import com.example.movieworld.movie.dto.MovieListResDto;
import com.example.movieworld.user.domain.UserDetailsImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
@Slf4j
public class LikeController {
    private final LikeService likeService;
    // ⭐ @PostConstruct 어노테이션을 사용하여 빈 초기화 후 로그 찍기
    @PostConstruct
    public void init() {
        log.info("############ LikeController 빈 초기화 완료! ############");
    }

    @PostMapping("/{movieId}")
    public ResponseEntity<String> likeMovie(@AuthenticationPrincipal UserDetailsImpl userDetail,
                                            @PathVariable Long movieId){
        log.info("@@@@Like Movie@@@@");
        likeService.likeMovie(movieId,userDetail.getUser().getId());
        return ResponseEntity.ok("좋아요 처리에 성공하였습니다.");
    }
    @DeleteMapping("/unlike/{movieId}")
    public ResponseEntity<String> unlikeMovie(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @PathVariable Long movieId){
        log.info("@@@@unlikeMovie@@@@");
        likeService.unlikeMovie(movieId,userDetails.getUser().getId());
        return ResponseEntity.ok("좋아요 취소 처리에 성공하였습니다.");
    }

    @GetMapping("/movie")
    public ResponseEntity<Page<MovieListResDto>> getLikedMovieList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                   @RequestParam int pageNum){
        return ResponseEntity.ok(
                likeService.getLikedMovieList(pageNum,userDetails.getUser().getId())
        );
    }

}
