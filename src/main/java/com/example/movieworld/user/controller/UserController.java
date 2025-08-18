package com.example.movieworld.user.controller;

import com.example.movieworld.user.dto.SignUpReqDto;
import com.example.movieworld.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@ModelAttribute SignUpReqDto signUpReqDto){
        userService.signUp(signUpReqDto);
        return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
    }
}
