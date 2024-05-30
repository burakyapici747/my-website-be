package com.blog.mywebsite.api.request;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Optional;

@Valid
public record CategoryPostRequest(
        @NotBlank(message = "Name cannot be empty or null.")
        @Size(max = 55, message = "Name field maximum must be 55 characters long.")
        String name,
        @Nullable
        @Size(min = 36, max = 36, message = "ParentId field must be 36 characters long.")
        Optional<String> parentId
){}