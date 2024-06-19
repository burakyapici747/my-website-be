package com.blog.mywebsite.api.request;

import com.blog.mywebsite.validation.ISO8601Validation;
import com.blog.mywebsite.validation.NullableSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ArticlePostRequest(
        @NullableSize(size = 36, message = "CategoryId field must be empty or 36 characters long.")
        String categoryId,
        @NotBlank(message = "Title cannot be empty or null.")
        @Size(min = 5, max = 255, message = "Title field must be between 5 and 255 characters long.")
        String title,
        @NotBlank(message = "Content cannot be empty or null.")
        @Size(min = 5, max = 1000, message = "Content field must be between 5 and 100 characters long.")
        String content,
        @Positive(message = "ReadingTime field must be positive.")
        int readingTime,
        @ISO8601Validation
        String publishDate
){}