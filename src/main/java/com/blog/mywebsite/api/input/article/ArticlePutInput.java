package com.blog.mywebsite.api.input.article;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import static com.blog.mywebsite.constant.ValidationConstant.*;

public record ArticlePutInput(
        @Nullable
        @Size(min = ARTICLE_TITLE_MIN_LENGTH, max = ARTICLE_TITLE_MAX_LENGTH, message = ARTICLE_TITLE_MESSAGE)
        String title,
        @Nullable
        @Size(min = ARTICLE_CONTENT_MIN_LENGTH, max = ARTICLE_CONTENT_MAX_LENGTH, message = ARTICLE_CONTENT_MESSAGE)
        String content,
        @Nullable
        @PositiveOrZero(message = READING_TIME_MESSAGE)
        Integer readingTime
){}