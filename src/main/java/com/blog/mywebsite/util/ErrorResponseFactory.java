package com.blog.mywebsite.util;

import com.blog.mywebsite.api.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ErrorResponseFactory {
    private ErrorResponseFactory(){
        throw new AssertionError();
    }

    public static ErrorResponse.Error createErrorResponse(
            Exception exception,
            HttpServletRequest httpServletRequest,
            HttpStatus httpStatus,
            String title,
            String detail
    ){
        return new ErrorResponse.Error(
                UUID.randomUUID().toString(),
                new ErrorResponse.Error.Links(
                        httpServletRequest.getRequestURL().toString(),
                        null
                ),
                String.valueOf(httpStatus.value()),
                httpStatus.getReasonPhrase(),
                title,
                detail,
                new ErrorResponse.Error.Source(
                    null,
                    null
                ),
                null
        );
    }
}
