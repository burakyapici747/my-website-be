package com.blog.mywebsite.api.request;

public record ArticlePutRequest(
        String title,
        String content,
        int readingTime,
        int rate
) { }
