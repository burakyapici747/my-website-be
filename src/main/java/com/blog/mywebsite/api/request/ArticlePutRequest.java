package com.blog.mywebsite.api.request;

import jakarta.validation.constraints.Positive;

public record ArticlePutRequest(
        String title,
        String content,
        @Positive(message = "ReadingTime sadece pozitif olabilir!")
        int readingTime,
        @Positive(message = "Rate sadece pozitifi olabilir!")
        int rate
) { }
