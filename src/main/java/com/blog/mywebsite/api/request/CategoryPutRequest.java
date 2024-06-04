package com.blog.mywebsite.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Valid
public record CategoryPutRequest(
        @NotBlank(message = "Id cannot be empty or null.")
        @Size(min = 36, max = 36, message = "ParentId field must be 36 characters long.")
        String id
) { }
