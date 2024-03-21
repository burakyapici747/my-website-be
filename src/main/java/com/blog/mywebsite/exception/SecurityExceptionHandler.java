package com.blog.mywebsite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

public class SecurityExceptionHandler {

    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ErrorDataResponse<Map<String, String>> handleEmailNotFoundException(EmailNotFoundException exception){
        return null;
    }
}
