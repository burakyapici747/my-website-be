package com.blog.mywebsite.api.request;

import com.blog.mywebsite.validation.DateByRangeValidation;
import com.blog.mywebsite.validation.ISO8601Validation;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
@DateByRangeValidation
public record ArticleGetByDateRange (
        @NotNull
        @ISO8601Validation
        LocalDate from,
        @NotNull
        @ISO8601Validation
        LocalDate to
){ }
