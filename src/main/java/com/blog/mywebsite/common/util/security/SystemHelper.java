package com.blog.mywebsite.common.util.security;

import com.blog.mywebsite.constant.EntityConstant;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.model.User;
import com.blog.mywebsite.security.EmailAuthenticationToken;
import com.blog.mywebsite.service.impl.CustomUserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SystemHelper {
    private final CustomUserDetailsService customUserDetailsService;

    public SystemHelper(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    public User getCurrentUser(){
        EmailAuthenticationToken emailAuthenticationToken =
                (EmailAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(emailAuthenticationToken)) {
            String userEmail = (String) emailAuthenticationToken.getEmail();
            return customUserDetailsService.getUserByEmail(userEmail);
        }
        throw new EntityNotFoundException(EntityConstant.USER_NOT_FOUND);
    }
}