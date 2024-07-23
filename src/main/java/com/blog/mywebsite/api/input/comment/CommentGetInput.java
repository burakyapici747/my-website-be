package com.blog.mywebsite.api.input.comment;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Size;

import static com.blog.mywebsite.constant.ValidationConstant.*;
import static com.blog.mywebsite.constant.ValidationConstant.COMMENT_PARENT_ID_SIZE_MESSAGE;

public record CommentGetInput(
        @Nullable
        @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ID_SIZE_MESSAGE)
        String id,
        @Nullable
        @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = COMMENT_PARENT_ID_SIZE_MESSAGE)
        String parentId
) {}