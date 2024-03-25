package com.blog.mywebsite.exception;

import com.blog.mywebsite.api.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

public class SecurityExceptionHandler {

    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BaseResponse<Map<String, String>> handleEmailNotFoundException(EmailNotFoundException exception){
        return null;
    }
}
