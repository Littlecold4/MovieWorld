package com.example.movieworld.user.service;

import com.example.movieworld.common.CustomException;
import com.example.movieworld.common.ErrorCode;
import com.example.movieworld.user.domain.User;
import com.example.movieworld.user.dto.SignUpReqDto;
import com.example.movieworld.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class SignupServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup(){

    }
    @Nested
    @DisplayName("Service _ 회원가입 성공")
    class Success{
        @Test
        @DisplayName("성공")
        void success(){
            //given
            SignUpReqDto signUpReqDto =new SignUpReqDto().builder()
                    .userEmail("test@test.com")
                    .userName("TEST_userName")
                    .password("test123!!")
                    .build();

            when(userRepository.existsByUserEmail(signUpReqDto.getUserEmail())).thenReturn(false);
            when(passwordEncoder.encode(signUpReqDto.getPassword())).thenReturn("TEST_encodedPassword");
            when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
                User user = invocation.getArgument(0);
                user.setId(1L);
                return user;
            });
            //when
            User user = userService.signUp(signUpReqDto);

            //then
            assertEquals(1L,user.getId());
            verify(userRepository,times(1)).existsByUserEmail(signUpReqDto.getUserEmail());
            verify(passwordEncoder,times(1)).encode(signUpReqDto.getPassword());
            verify(userRepository,times(1)).save(any(User.class));
        }
    }
    @Nested
    @DisplayName("Service _ 회원가입 실패")
    class Failure{
        @Test
        @DisplayName("실패 _ 중복된 이메일")
        void fail_Duplicated_UserEmail(){
            //given
            SignUpReqDto signUpReqDto =new SignUpReqDto().builder()
                    .userEmail("test@test.com")
                    .userName("TEST_userName")
                    .password("test123!!")
                    .build();

            when(userRepository.existsByUserEmail(signUpReqDto.getUserEmail())).thenReturn(true);
            //when
            Exception ex = assertThrows(CustomException.class,
                    ()-> userService.signUp(signUpReqDto));
            //then
            assertEquals(ErrorCode.DUPLICATED_USEREMAIL.getMessage(),
                    ex.getMessage());

            verify(userRepository,times(1)).existsByUserEmail(signUpReqDto.getUserEmail());
            verify(passwordEncoder,never()).encode(signUpReqDto.getPassword());
            verify(userRepository,never()).save(any(User.class));
        }

    }
}
