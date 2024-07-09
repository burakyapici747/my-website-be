package com.blog.mywebsite.exception;

import com.blog.mywebsite.api.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpServletResponse.SC_UNAUTHORIZED,
//                "Authentication required. Please log in to the system."
//        );

        List<ErrorResponse.Error> errorResponse = new ArrayList<>(List.of(
                new ErrorResponse.Error(
                        UUID.randomUUID().toString(),
                        null,
                        String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        "Title Error",
                        "The login information is incorrect. Please check your information and try again.",
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