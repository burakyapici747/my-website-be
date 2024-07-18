package com.blog.mywebsite.api.input.article;

import com.blog.mywebsite.validation.ISO8601Validation;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ArticleGetByDateRangeInput(
        @NotNull(message = "From date cannot be null")
        @ISO8601Validation
        LocalDate from,
        @NotNull(message = "To date cannot be null")
        @ISO8601Validation
        LocalDate to
){}