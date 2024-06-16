package com.blog.mywebsite.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CookieAuthenticationFilter extends OncePerRequestFilter {
    public static final String COOKIE_NAME = "auth_by_cookie";
    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain
    ) throws ServletException, IOException {



        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void addCookieToResponse(HttpServletResponse httpServletResponse, String token){
        Cookie authenticationCookie = new Cookie(COOKIE_NAME, token);
        authenticationCookie.setHttpOnly(true);
        authenticationCookie.setSecure(true);
        authenticationCookie.setMaxAge(24 * 60 * 60);
        httpServletResponse.addCookie(authenticationCookie);
    }
}
