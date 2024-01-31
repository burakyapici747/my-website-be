package com.blog.mywebsite.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

//TODO: message alanlarını ingilizce ile değiştir
public record ArticlePostRequest(
        @NotBlank(message = "Title field cannot be empty!")
        @Size(max = 100, message = "Title field must be at most 100 characters!")
        String title,

        @NotBlank(message = "Content field cannot be empty!")
        @Size(min = 10, max = 1000, message = "Content field must be between 10 and 1000 characters")
        String content,

        @Positive(message = "Reading Time sadece pozitif olabilir!")
        int readingTime,
        @Positive(message = "Rate sadece pozitif olabilri")
        long rate
){}
