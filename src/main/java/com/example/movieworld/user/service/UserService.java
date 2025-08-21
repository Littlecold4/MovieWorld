package com.example.movieworld.user.service;

import com.example.movieworld.common.CustomException;
import com.example.movieworld.common.ErrorCode;
import com.example.movieworld.jwt.TokenProvider;
import com.example.movieworld.user.domain.User;
import com.example.movieworld.user.dto.LoginReqDto;
import com.example.movieworld.user.dto.SignUpReqDto;
import com.example.movieworld.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.http.*;


@Service
@RequiredArgsConstructor
public class UserService {
     private final   UserRepository userRepository;
     private final   PasswordEncoder passwordEncoder;
     private final TokenProvider tokenProvider;

    public User signUp(SignUpReqDto signUpReqDto){
        if(userRepository.existsByUserEmail(signUpReqDto.getUserEmail()))
            throw new CustomException(ErrorCode.DUPLICATED_USEREMAIL);
        User user = new User(signUpReqDto.getUserEmail(), signUpReqDto.getUserName(),
                passwordEncoder.encode(signUpReqDto.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public HttpHeaders loginUser(LoginReqDto loginReqDto){
        if(!userRepository.existsByUserEmail(loginReqDto.getUserEmail()))
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        User user = userRepository.findByUserEmail(loginReqDto.getUserEmail());

        if(!passwordEncoder.matches(loginReqDto.getPassword(), user.getPassword()))
            throw new CustomException(ErrorCode.INVALID_PASSWORD);

        String token = tokenProvider.generateJwtToken(loginReqDto.getUserEmail());
        HttpHeaders headers =new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION,"Bearer "+token);

        return headers;
    }


}
