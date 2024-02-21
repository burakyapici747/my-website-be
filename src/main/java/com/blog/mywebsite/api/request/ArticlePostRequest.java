package com.blog.mywebsite.api.request;

import com.blog.mywebsite.validation.ISO8601Validation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ArticlePostRequest(
        @NotBlank(message = "Title cannot be empty.")
        @Size(min = 10, max = 50, message = "Title field must be between 100 and 100 characters long.")
        String title,
        @NotBlank(message = "Content cannot be empty.")
        @Size(min = 10, max = 1000, message = "Content field must be between 100 and 100 characters long.")
        String content,
        @Positive(message = "ReadingTime field must be positive.")
        int readingTime,
        @ISO8601Validation
        LocalDateTime publishDate
){}