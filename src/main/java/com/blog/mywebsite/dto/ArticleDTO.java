package com.blog.mywebsite.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ArticleDTO(
        String title,
        String content,
        int readingTime,
        long rate,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
        LocalDateTime publishDate,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
        LocalDateTime createdAt
){ }