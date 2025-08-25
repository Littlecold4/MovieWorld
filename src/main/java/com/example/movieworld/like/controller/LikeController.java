package com.example.movieworld.like.controller;

import com.example.movieworld.like.service.LikeService;
import com.example.movieworld.user.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    @PostMapping("/{movieId}")
    public ResponseEntity<String> likeMovie(@AuthenticationPrincipal UserDetailsImpl userDetail,
                                            @PathVariable Long movieId){
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        likeService.likeMovie(movieId,userDetail.getUser().getId());
        return ResponseEntity.ok("좋아요 처리에 성공하였습니다.");
    }

}
