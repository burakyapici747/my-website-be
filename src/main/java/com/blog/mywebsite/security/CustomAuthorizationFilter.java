package com.blog.mywebsite.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.blog.mywebsite.api.response.ErrorDataResponse;
import com.blog.mywebsite.model.CustomUserDetails;
import com.blog.mywebsite.security.jwt.JWTStrategy;
import com.blog.mywebsite.security.jwt.JWTStrategyFactory;
import com.blog.mywebsite.service.impl.CustomUserDetailsService;
import com.blog.mywebsite.common.util.security.JWTHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.blog.mywebsite.constant.APIConstant.LOGIN;

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
        if(request.getServletPath().equals(LOGIN)){
            filterChain.doFilter(request, response);
            return;
        }

        Optional<JWTStrategy> jwtStrategyOptional = JWTStrategyFactory.getStrategy(request);
        jwtStrategyOptional.ifPresent(jwtStrategy -> {
            try {
                processJWTToken(jwtStrategy, request, response);
            } catch (IOException e) {
                throw new JWTVerificationException(e.getMessage());
            }
        });

        filterChain.doFilter(request, response);
    }

    private void processJWTToken(
            JWTStrategy jwtStrategy,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authorizationToken = jwtStrategy.extractToken();

        try{
            final DecodedJWT decodedJWT = JWTHelper.decodeJwtToken(authorizationToken);
            final String email = decodedJWT.getSubject();
            final CustomUserDetails customUserDetails =
                    (CustomUserDetails) customUserDetailsService.loadUserByUsername(email);
            final EmailAuthenticationToken emailAuthenticationToken =
                    new EmailAuthenticationToken(
                            customUserDetails.getEmail(),
                            null,
                            customUserDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(emailAuthenticationToken);
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
    }
}