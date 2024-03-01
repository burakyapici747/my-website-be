package com.blog.mywebsite.api.request;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ArticlePutRequest(
        @Size(min = 5, max = 50, message = "Title field must be between 5 and 255 characters long.")
        String title,

        @Size(min = 5, max = 1000, message = "Content field must be between 5 and 100 characters long.")
        String content,

        @PositiveOrZero(message = "ReadingTime field must be positive.")
        Integer readingTime
) { }
