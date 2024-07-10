package com.blog.mywebsite.api.request;

import com.blog.mywebsite.validation.ISO8601Validation;
import com.blog.mywebsite.validation.NullableSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import static com.blog.mywebsite.constant.ValidationConstant.*;

public record ArticlePostRequest(
        @NullableSize(message = CATEGORY_ID_SIZE_MESSAGE)
        String categoryId,
        @NotBlank(message = ARTICLE_TITLE_BLANK_MESSAGE)
        @Size(min = ARTICLE_TITLE_MIN_LENGTH, max = ARTICLE_TITLE_MAX_LENGTH, message = ARTICLE_TITLE_MESSAGE)
        String title,
        @NotBlank(message = ARTICLE_CONTENT_BLANK_MESSAGE)
        @Size(min = ARTICLE_CONTENT_MIN_LENGTH, max = ARTICLE_CONTENT_MAX_LENGTH, message = ARTICLE_CONTENT_MESSAGE)
        String content,
        @Positive(message = READING_TIME_MESSAGE)
        int readingTime,
        //@ISO8601Validation
        String publishDate
){}