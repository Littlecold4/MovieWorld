package com.example.movieworld.common;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomException extends RuntimeException{
    public ErrorCode errorCode;

    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
    public CustomException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
