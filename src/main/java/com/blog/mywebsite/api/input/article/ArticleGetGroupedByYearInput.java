package com.blog.mywebsite.api.input.article;

import com.blog.mywebsite.enumerator.SearchOperation;
import com.blog.mywebsite.validation.ISO8601Validation;
import io.micrometer.common.lang.Nullable;

import java.time.LocalDate;

public record ArticleGetGroupedByYearInput(
        @Nullable
        @ISO8601Validation
        LocalDate publishDate,
        @Nullable
        SearchOperation searchOperation
){}