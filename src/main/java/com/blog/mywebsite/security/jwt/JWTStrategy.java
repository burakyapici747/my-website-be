package com.blog.mywebsite.security.jwt;

import jakarta.servlet.http.HttpServletRequest;

public interface JWTStrategy {
    String extractToken();
}
