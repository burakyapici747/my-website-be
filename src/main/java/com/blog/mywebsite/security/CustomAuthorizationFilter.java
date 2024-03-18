package com.blog.mywebsite.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.blog.mywebsite.constant.SecurityConstant;
import com.blog.mywebsite.model.CustomUserDetails;
import com.blog.mywebsite.service.impl.CustomUserDetailsService;
import com.blog.mywebsite.util.security.JWTHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
             HttpServletRequest request,
             HttpServletResponse response,
             FilterChain filterChain) throws ServletException, IOException {
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
                }catch (JWTVerificationException e){
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());


                    final Map<String, String> errorData = new HashMap<>();
                    errorData.put("error", e.getMessage());
                    errorData.put("stackTrace", Arrays.toString(e.getStackTrace()));
                    errorData.put("path", request.getPathInfo());
                    errorData.put("time", LocalDateTime.now().toString());

                    new ObjectMapper().writeValue(response.getOutputStream(), errorData);
                }
            }else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
