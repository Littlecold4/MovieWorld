package com.example.movieworld.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    MOVIE_ALREADY_UNLIKED(HttpStatus.BAD_REQUEST,"이미 좋아요가 취소된 영화입니다"),
    MOVIE_ALREADY_LIKED(HttpStatus.BAD_REQUEST,"이미 좋아요를 누른 영화입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다."),
    DUPLICATED_USEREMAIL(HttpStatus.BAD_REQUEST,"중복된 이메일입니다."),
    INVALID_GENRE_ID(HttpStatus.BAD_REQUEST,"잘못된 장르 정보입니다."),
    INVALID_PAGE_NUMBER(HttpStatus.BAD_REQUEST,"잘못된 페이지 넘버입니다."),
    MOVIE_NOT_EXIST(HttpStatus.BAD_REQUEST,"존재하지 않는 영화정보입니다."),
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST,"존재하지 않는 유저입니다.");
    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
