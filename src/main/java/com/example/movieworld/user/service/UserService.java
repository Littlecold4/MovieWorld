package com.example.movieworld.user.service;

import com.example.movieworld.common.CustomException;
import com.example.movieworld.common.ErrorCode;
import com.example.movieworld.user.domain.User;
import com.example.movieworld.user.dto.SignUpReqDto;
import com.example.movieworld.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signUp(SignUpReqDto signUpReqDto){
        if(userRepository.existsByUserEmail(signUpReqDto.getUserEmail()))
            throw new CustomException(ErrorCode.DUPLICATED_USEREMAIL);
        User user = new User(signUpReqDto.getUserEmail(), signUpReqDto.getUserName(),
                passwordEncoder.encode(signUpReqDto.getPassword()));
        return userRepository.save(user);
    }


}
