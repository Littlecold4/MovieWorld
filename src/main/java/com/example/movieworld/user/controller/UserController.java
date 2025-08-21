package com.example.movieworld.user.controller;

import com.example.movieworld.user.dto.LoginReqDto;
import com.example.movieworld.user.dto.SignUpReqDto;
import com.example.movieworld.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @ModelAttribute SignUpReqDto signUpReqDto){
        userService.signUp(signUpReqDto);
        return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<String> userLogin(@ModelAttribute LoginReqDto loginReqDto){
        HttpHeaders headers = userService.loginUser(loginReqDto);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body("로그인 완료");
    }
}
