package com.blog.mywebsite.security.jwt;

import com.blog.mywebsite.constant.SecurityConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

public class AuthorizationHeaderStrategy implements JWTStrategy{
    private final HttpServletRequest request;

    public AuthorizationHeaderStrategy(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String extractToken() {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }
}
