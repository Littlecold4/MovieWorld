package com.example.movieworld.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.swing.text.html.parser.Entity;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    protected ResponseEntity<String> handleCustomException(CustomException ex){
        log.warn("CustomException 발생: message={}, expectedStatus={}", ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        String message = ex.getMessage().split("default message")[2].replace("[","").replace("]","");
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }
}
