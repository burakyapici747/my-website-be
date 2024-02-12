package com.blog.mywebsite.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ArticlePostRequest(
        @NotBlank(message = "Title cannot be empty.")
        @Size(max = 100, message = "Title can be maximum 100 characters long.")
        String title,

        @NotBlank(message = "Content cannot be empty.")
        @Size(min = 10, max = 1000, message = "Content field must be between 10 and 100 characters long.")
        String content,

        @Positive(message = "ReadingTime cannot be negative.")
        int readingTime,
        @Positive(message = "Rate cannot be negative.")
        long rate
){}
