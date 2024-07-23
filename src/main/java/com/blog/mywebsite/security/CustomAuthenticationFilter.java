package com.blog.mywebsite.security;

import com.blog.mywebsite.api.request.UserLoginRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.common.util.security.JWTHelper;
import com.blog.mywebsite.model.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import static com.blog.mywebsite.constant.APIConstant.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final AuthenticationManager authenticationManager;
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(USER_LOGIN_URL, "POST");
    private String emailParameter = "email";
    private boolean postOnly = true;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        attemptAuthenticationValidations(request);
        final String email = obtainEmail(request);
        final EmailAuthenticationToken token = new EmailAuthenticationToken(email, null);
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication)
            throws IOException {
        CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();
        String email = customUserDetails.getEmail();

        String accessToken = JWTHelper.generateJwtToken(email, authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());

        Map<String, String> data = new HashMap<>();
        data.put("access_token", accessToken);

        BaseResponse<Map<String, String>> responseData = new BaseResponse<>(
                null,
                data
        );
        new ObjectMapper().writeValue(response.getOutputStream(), responseData);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException, ServletException {
        this.securityContextHolderStrategy.clearContext();
        this.getRememberMeServices().loginFail(request, response);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        this.getFailureHandler().onAuthenticationFailure(request, response, failed);
    }

    @Nullable
    protected String obtainEmail(HttpServletRequest request) throws IOException {
        UserLoginRequest userLoginRequest = new ObjectMapper()
                .readValue(request.getInputStream(), UserLoginRequest.class);
        return userLoginRequest.getEmail();
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setEmailParameter(String emailParameter) {
        Assert.hasText(emailParameter, "Email parameter must not be empty or null");
        this.emailParameter = emailParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getEmailParameter() {
        return this.emailParameter;
    }

    public void attemptAuthenticationValidations(HttpServletRequest request){
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        if(!request.getContentType().contains("application/json")){
            throw new AuthenticationServiceException("ContentType must be application/json");
        }

        //Email boş gelme durumunu vs. kontrol et eğer boş işe client'e success false dön.
    }
}