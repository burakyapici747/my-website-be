package com.blog.mywebsite.api.output;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ArticleOutput(
        String type,
        Attributes attributes
){
    public record Attributes(
            String id,
            String title,
            String content,
            int readingTime,
            int rate,
            LocalDate publishDate,
            LocalDateTime createdAt
    ){}
}