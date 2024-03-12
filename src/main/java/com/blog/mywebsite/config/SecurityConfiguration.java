package com.blog.mywebsite.config;

import com.blog.mywebsite.security.CustomAuthorizationFilter;
import com.blog.mywebsite.security.JWTAuthenticationFilter;
import com.blog.mywebsite.service.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;

    public SecurityConfiguration(JWTAuthenticationFilter jwtAuthenticationFilter, CustomUserDetailsService customUserDetailsService, AuthenticationManager authenticationManager) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/user/login").permitAll();
                    auth.requestMatchers("/api/user/register").permitAll();
                })
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(new CustomAuthorizationFilter(customUserDetailsService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}