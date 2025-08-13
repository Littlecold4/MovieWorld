package com.example.movieworld.user.service;

import com.example.movieworld.user.domain.User;
import com.example.movieworld.user.dto.SignUpReqDto;
import com.example.movieworld.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User singUp(SignUpReqDto signUpReqDto){
        return null;
    }


}
