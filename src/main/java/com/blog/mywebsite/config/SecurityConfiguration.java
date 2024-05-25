package com.blog.mywebsite.config;

import com.blog.mywebsite.enumerator.Role;
import com.blog.mywebsite.exception.CustomAccessDeniedHandler;
import com.blog.mywebsite.exception.CustomAuthenticationEntryPoint;
import com.blog.mywebsite.exception.CustomAuthenticationFailureHandler;
import com.blog.mywebsite.security.CustomAuthenticationFilter;
import com.blog.mywebsite.security.CustomAuthorizationFilter;
import com.blog.mywebsite.security.EmailAuthenticationProvider;
import com.blog.mywebsite.service.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    public SecurityConfiguration(CustomUserDetailsService customUserDetailsService, AuthenticationConfiguration authenticationConfiguration) {
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter =
                new CustomAuthenticationFilter(getAuthenticationManager(authenticationConfiguration), customAuthenticationFailureHandler());

        return http
                .csrf(AbstractHttpConfigurer::disable)
                //.cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/user/login", "/api/user/register").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/api/article/**", "/api/comment/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/api/comment/**").permitAll();
                })
//                .authorizeHttpRequests(auth-> {
//                    auth.requestMatchers(HttpMethod.POST, "/api/article/**").hasAuthority("ADMIN");
//                    auth.requestMatchers(HttpMethod.PUT, "/api/article/**").hasAuthority("ADMIN");
//                    auth.requestMatchers(HttpMethod.DELETE, "/api/article/**").hasAuthority("ADMIN");
//                })
                .authorizeHttpRequests(auth-> {
                    auth.requestMatchers(HttpMethod.POST, "/api/comment/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/api/comment/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/comment/**").hasAuthority("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .exceptionHandling(exception -> {
                    exception.accessDeniedHandler(accessDeniedHandler());
                    exception.authenticationEntryPoint(authenticationEntryPoint());
                })
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .addFilterBefore(customAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(new CustomAuthorizationFilter(customUserDetailsService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new EmailAuthenticationProvider(customUserDetailsService);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}