package com.example.movieworld.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
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
