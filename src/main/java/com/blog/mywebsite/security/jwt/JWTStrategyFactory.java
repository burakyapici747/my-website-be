package com.blog.mywebsite.security.jwt;

import com.blog.mywebsite.constant.SecurityConstant;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

public final class JWTStrategyFactory {
    private JWTStrategyFactory(){throw new AssertionError();}
    public static Optional<JWTStrategy> getStrategy(HttpServletRequest request) {
        if (hasAuthorizationHeader(request)) {
            return Optional.of(new AuthorizationHeaderStrategy(request));
        } else if (hasCookie(request)) {
            return Optional.of(new CookieStrategy(request));
        } else {
            return Optional.empty();
        }
    }

    private static boolean hasCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasAuthorizationHeader(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authorizationHeader != null && authorizationHeader.startsWith(SecurityConstant.AUTHORIZATION_TOKEN_PREFIX);
    }
}