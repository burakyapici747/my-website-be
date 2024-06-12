package com.blog.mywebsite.api.request;

import com.blog.mywebsite.validation.NullableSize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Valid
public record CategoryPostRequest(
        @NullableSize(size = 36, message = "ParentId field must be empty or 36 characters long.")
        String parentId,
        @NotBlank(message = "Name cannot be empty or null.")
        @Size(max = 55, message = "Name field maximum must be 55 characters long.")
        String name
){}