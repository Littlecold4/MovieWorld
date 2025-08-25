package com.example.movieworld.like.controller;

import com.example.movieworld.like.service.LikeService;
import com.example.movieworld.user.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    @PostMapping("/{movieId}")
    public ResponseEntity<String> likeMovie(@AuthenticationPrincipal UserDetailsImpl userDetail,
                                            @PathVariable Long movieId){
        likeService.likeMovie(movieId,userDetail.getUser().getId());
        return ResponseEntity.ok("좋아요 처리에 성공하였습니다.");
    }

    @DeleteMapping("/unlike/{movieId}")
    public ResponseEntity<String> unlikeMovie(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @PathVariable Long movieId){
        likeService.unlikeMovie(movieId,userDetails.getUser().getId());
        return ResponseEntity.ok("좋아요 취소 처리에 성공하였습니다.");
    }

}
