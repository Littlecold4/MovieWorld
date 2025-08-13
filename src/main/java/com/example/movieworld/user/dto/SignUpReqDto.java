package com.example.movieworld.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SignUpReqDto {
    @NotEmpty
    @Email(message = "이메일 형식이 아닙니다.")
    private String userEmail;
    @NotEmpty
    private String userName;
    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,12}$",
            message = "비밀번호는 8자 이상 12자 이하의 길이의 영문자, 숫자, 특수문자(!@#$%^&*)만 사용 가능합니다.")
    private String password;
}
