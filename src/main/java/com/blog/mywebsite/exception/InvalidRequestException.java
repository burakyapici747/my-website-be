package com.blog.mywebsite.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends RuntimeException{
    private HttpStatus httpStatusCode;
    private String[] errorMessage;

    public InvalidRequestException(HttpStatus httpStatusCode, String[] errorMessages){
        super();
        this.httpStatusCode = httpStatusCode;
        this.errorMessage = errorMessages;
    }
}