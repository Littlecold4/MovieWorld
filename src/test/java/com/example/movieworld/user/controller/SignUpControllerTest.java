package com.example.movieworld.user.controller;

import com.example.movieworld.config.WebSecurityConfig;
import com.example.movieworld.jwt.TokenProvider;
import com.example.movieworld.user.domain.User;
import com.example.movieworld.user.dto.SignUpReqDto;
import com.example.movieworld.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(UserController.class)
@Import(WebSecurityConfig.class)
public class SignUpControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private TokenProvider tokenProvider;

    @MockitoBean
    private UserService userService;
    private User user;
    private SignUpReqDto signUpReqDto;
    @BeforeEach
    void setup(){
    }
    @Nested
    @DisplayName("Controller _ 회원가입 성공")
    class Success{
        @Test
        @DisplayName("Controller _ 회원가입 성공")
        void success_signUp() throws Exception{
            signUpReqDto = new SignUpReqDto().builder()
                    .userEmail("test@test.com")
                    .userName("TEST_userName")
                    .password("test123!!")
                    .build();

            user = new User().builder()
                    .userEmail("test@test.com")
                    .userName("TEST_userName")
                    .password("EncodedPassword")
                    .build();
            when(userService.signUp(any(SignUpReqDto.class))).thenReturn(user);

            mvc.perform(MockMvcRequestBuilders.post("/user/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signUpReqDto)))
                    .andExpect(MockMvcResultMatchers.status().is(200))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("Controller _ 회원가입 실패")
    class Failure{
        @Test
        @DisplayName("실패 _ 잘못된 이메일 형식")
        void fail_Invalid_Email_Form() throws Exception {
            signUpReqDto = new SignUpReqDto().builder()
                    .userEmail("testEmail")
                    .userName("TEST_userName")
                    .password("test123!!")
                    .build();

            mvc.perform(MockMvcRequestBuilders.post("/user/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpReqDto)))
                    .andExpect(MockMvcResultMatchers.status().is(400))
                    .andDo(print());

            verify(userService,never()).signUp(signUpReqDto);
        }
    }

    @Test
    @DisplayName("실패 _잘못된 비밀번호 형식")
    void fail_Invalid_Password_Form()throws Exception{
        signUpReqDto = new SignUpReqDto().builder()
                .userEmail("test@test.com")
                .userName("TEST_userName")
                .password("TEST_password")
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpReqDto)))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andDo(print());

        verify(userService,never()).signUp(signUpReqDto);
    }

    @Test
    @DisplayName("실패 _ 이메일이나 이름 미입력")
    void fail_Empty_UserEmail_UserName()throws Exception{
        signUpReqDto = new SignUpReqDto().builder()
                .userEmail("test@test.com")
                .password("test123!!")
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpReqDto)))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andDo(print());

        verify(userService,never()).signUp(signUpReqDto);
    }
}
