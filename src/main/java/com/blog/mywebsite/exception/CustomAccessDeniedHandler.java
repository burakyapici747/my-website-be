package com.blog.mywebsite.exception;

import com.blog.mywebsite.api.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpServletResponse.SC_UNAUTHORIZED,
//                "You do not have permission to access this resource."
//        );
        List<ErrorResponse.Error> errorResponse = new ArrayList<>(List.of(
                new ErrorResponse.Error(
                        UUID.randomUUID().toString(),
                        null,
                        String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        "Title Error",
                        "You do not have permission to access this resource.",
                        null,
                        new ErrorResponse.Error.Meta(
                                UUID.randomUUID().toString(),
                                LocalDateTime.now(),
                                "Version"
                        )
                )
        ));
        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
    }
}