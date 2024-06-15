package com.blog.mywebsite.security.jwt;

import jakarta.servlet.http.HttpServletRequest;

public class CookieStrategy implements JWTStrategy{

    private final HttpServletRequest request;

    public CookieStrategy(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String extractToken() {
        return null;
    }
}
