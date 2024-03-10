package com.blog.mywebsite.security;

import com.blog.mywebsite.enumerator.Role;
import com.blog.mywebsite.model.User;
import com.blog.mywebsite.util.security.JWTHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        String email = request.getParameter("email");

        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, null);

        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult){
        final User user = (User) authResult.getPrincipal();

        final String accessToken = JWTHelper.generateJwtToken(user.getEmail(), user.getRoles()
                .stream().map(Role::getValue).collect(Collectors.toList()));
    }
}