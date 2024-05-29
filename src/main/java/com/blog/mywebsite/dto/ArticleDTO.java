package com.blog.mywebsite.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;

public record ArticleDTO(
        String title,
        String content,
        int readingTime,
        int rate,
        LocalDate publishDate,
        LocalDateTime createdAt
){ }