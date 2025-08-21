package com.example.movieworld.user.service;

import com.example.movieworld.user.domain.User;
import com.example.movieworld.user.domain.UserDetailsImpl;
import com.example.movieworld.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {
    private final UserRepository userRepository;

    public UserDetails loadUserByUserName(String userEmail){
        User user = userRepository.findByUserEmail(userEmail);
        return new UserDetailsImpl(user);
    }

}
