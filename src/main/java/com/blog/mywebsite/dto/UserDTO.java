package com.blog.mywebsite.dto;

import java.time.LocalDateTime;

public record UserDTO (
        String email,
        String name,
        LocalDateTime createdAt
){ }