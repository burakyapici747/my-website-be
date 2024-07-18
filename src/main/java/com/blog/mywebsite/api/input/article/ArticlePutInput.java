package com.blog.mywebsite.api.input.article;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import static com.blog.mywebsite.constant.ValidationConstant.*;

public record ArticlePutInput(
        @Size(min = ARTICLE_TITLE_MIN_LENGTH, max = ARTICLE_TITLE_MAX_LENGTH, message = ARTICLE_TITLE_MESSAGE)
        String title,
        @Size(min = ARTICLE_CONTENT_MIN_LENGTH, max = ARTICLE_CONTENT_MAX_LENGTH, message = ARTICLE_CONTENT_MESSAGE)
        String content,
        @PositiveOrZero(message = READING_TIME_MESSAGE)
        Integer readingTime
){}