package com.example.movieworld.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginReqDto {
    private String userEmail;
    private String password;

}
