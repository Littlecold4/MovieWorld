package com.example.movieworld.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@AllArgsConstructor
@Setter
@Builder
@NoArgsConstructor
public class SignUpReqDto {
    @NotEmpty
    @Email(message = "이메일 형식이 아닙니다.")
    private String userEmail;
    @NotEmpty
    private String userName;
    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$",
            message = "비밀번호는 8자 이상 16자 이하의 길이의 영문자, 숫자, 특수문자(!@#$%^&*)만 사용 가능합니다.")
    private String password;
}
