package com.blog.mywebsite.api.request;

import com.blog.mywebsite.validation.ISO8601Validation;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import static com.blog.mywebsite.constant.ValidationConstant.*;

public record ArticleGetRequest(
        @Nullable
        @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ID_SIZE_MESSAGE)
        String id,
        @Nullable
        @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = CATEGORY_ID_SIZE_MESSAGE)
        String categoryId,
        @Nullable
        @Size(min = CATEGORY_NAME_MIN_LENGTH, max = CATEGORY_NAME_MAX_LENGTH, message = CATEGORY_NAME_SIZE_MESSAGE)
        String categoryName,
        @Nullable
        @Size(min = ARTICLE_TITLE_MIN_LENGTH, max = ARTICLE_TITLE_MAX_LENGTH, message = ARTICLE_TITLE_MESSAGE)
        String title,
        @Nullable
        @ISO8601Validation
        LocalDate publishDate,
        @Nullable
        @Positive(message = READING_TIME_MESSAGE)
        Integer readingTime
){}