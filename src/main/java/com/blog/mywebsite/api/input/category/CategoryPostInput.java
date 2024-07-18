package com.blog.mywebsite.api.input.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.blog.mywebsite.constant.ValidationConstant.*;

public record CategoryPostInput(
        @Size(message = CATEGORY_ID_SIZE_MESSAGE)
        String parentId,
        @NotBlank(message = CATEGORY_NAME_BLANK_MESSAGE)
        @Size(min = CATEGORY_NAME_MIN_LENGTH, max = CATEGORY_NAME_MAX_LENGTH, message = CATEGORY_NAME_SIZE_MESSAGE)
        String name
) {}