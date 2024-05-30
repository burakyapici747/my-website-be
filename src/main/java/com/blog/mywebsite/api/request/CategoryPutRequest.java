package com.blog.mywebsite.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Valid
public record CategoryPutRequest(
        @NotBlank
        @Size
        String id
) { }
