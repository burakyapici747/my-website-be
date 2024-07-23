package com.blog.mywebsite.api.input.article;

import com.blog.mywebsite.validation.ISO8601Validation;

import java.time.LocalDate;
public record ArticleGetByDateRangeInput(
        @ISO8601Validation
        LocalDate from,
        @ISO8601Validation
        LocalDate to
){}