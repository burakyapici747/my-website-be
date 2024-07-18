package com.blog.mywebsite.api.input.category;

import jakarta.validation.constraints.Size;

import static com.blog.mywebsite.constant.ValidationConstant.*;
import static com.blog.mywebsite.constant.ValidationConstant.CATEGORY_NAME_SIZE_MESSAGE;

public record CategoryPutInput(
        @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = CATEGORY_PARENT_ID_MESSAGE)
        String parentId,
        @Size(min = CATEGORY_NAME_MIN_LENGTH, max = CATEGORY_NAME_MAX_LENGTH, message = CATEGORY_NAME_SIZE_MESSAGE)
        String name
) {}