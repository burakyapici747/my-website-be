package com.blog.mywebsite.api.input.article;

import com.blog.mywebsite.validation.ISO8601Validation;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import static com.blog.mywebsite.constant.ValidationConstant.*;

public record ArticleGetInput(
        @Nullable
        @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ID_SIZE_MESSAGE)
        String id,
        @Nullable
        @Size(min = ARTICLE_TITLE_MIN_LENGTH, max = ARTICLE_TITLE_MAX_LENGTH, message = ARTICLE_TITLE_MESSAGE)
        String title,
        @Nullable
        @ISO8601Validation
        LocalDate publishDate,
        @Nullable
        @Positive(message = READING_TIME_MESSAGE)
        Integer readingTime
) {}