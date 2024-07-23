package com.blog.mywebsite.api.input.article;

import com.blog.mywebsite.validation.ISO8601Validation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import static com.blog.mywebsite.constant.ValidationConstant.*;
import static com.blog.mywebsite.constant.ValidationConstant.READING_TIME_MESSAGE;

public record ArticlePostInput(
        @NotBlank(message = CATEGORY_ID_BLANK_MESSAGE)
        @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = CATEGORY_ID_SIZE_MESSAGE)
        String categoryId,
        @NotBlank(message = ARTICLE_TITLE_BLANK_MESSAGE)
        @Size(min = ARTICLE_TITLE_MIN_LENGTH, max = ARTICLE_TITLE_MAX_LENGTH, message = ARTICLE_TITLE_MESSAGE)
        String title,
        @NotBlank(message = ARTICLE_CONTENT_BLANK_MESSAGE)
        @Size(min = ARTICLE_CONTENT_MIN_LENGTH, max = ARTICLE_CONTENT_MAX_LENGTH, message = ARTICLE_CONTENT_MESSAGE)
        String content,
        @Positive(message = READING_TIME_MESSAGE)
        int readingTime,
        @ISO8601Validation
        String publishDate
){}