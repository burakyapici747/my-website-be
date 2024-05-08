package com.blog.mywebsite.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.blog.mywebsite.api.response.ErrorDataResponse;
import com.blog.mywebsite.api.response.ErrorResponse;
import com.blog.mywebsite.constant.SecurityConstant;
import com.blog.mywebsite.model.CustomUserDetails;
import com.blog.mywebsite.service.impl.CustomUserDetailsService;
import com.blog.mywebsite.util.security.JWTHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.lang.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomAuthorizationFilter extends OncePerRequestFilter{
    private final CustomUserDetailsService customUserDetailsService;

    public CustomAuthorizationFilter(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
             @Nullable HttpServletRequest request,
             @Nullable HttpServletResponse response,
             @Nullable FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/api/user/login")){
            filterChain.doFilter(request, response);
        }else{
            final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if(Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith(SecurityConstant.AUTHORIZATION_TOKEN_PREFIX)){
                final String authorizationToken = authorizationHeader.substring(SecurityConstant.AUTHORIZATION_TOKEN_PREFIX.length());

                try{
                    final DecodedJWT decodedJWT = JWTHelper.decodeJwtToken(authorizationToken);
                    final String email = decodedJWT.getSubject();
                    final CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(email);
                    final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(customUserDetails.getEmail(), null, customUserDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    filterChain.doFilter(request, response);
                }catch (JWTVerificationException error){
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());

                    final Map<String, String> errorData = new HashMap<>();
                    errorData.put("path", request.getServletPath());
                    errorData.put("time", LocalDateTime.now().toString());

                    ErrorDataResponse<Map<String, String>> errorDataResponse = new ErrorDataResponse<>(
                            HttpServletResponse.SC_OK,
                            error.getMessage(),
                            errorData
                    );

                    new ObjectMapper().writeValue(response.getOutputStream(), errorDataResponse);
                }
            }else {
                filterChain.doFilter(request, response);
            }
        }
    }
}