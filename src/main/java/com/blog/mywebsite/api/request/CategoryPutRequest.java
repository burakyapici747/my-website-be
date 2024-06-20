package com.blog.mywebsite.api.request;

import com.blog.mywebsite.validation.NullableSize;
import jakarta.validation.constraints.Size;

public record CategoryPutRequest(
        @NullableSize(message = "ParentId field must be empty or 36 characters long.")
        String parentId,
        @Size(max = 55, message = "Name field maximum must be 55 characters long.")
        String name
) { }
