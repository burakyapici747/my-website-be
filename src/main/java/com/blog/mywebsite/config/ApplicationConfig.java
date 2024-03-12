package com.blog.mywebsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

@Configuration
public class ApplicationConfig {
    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager();
    }
}
