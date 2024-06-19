package com.blog.mywebsite.dto;

public record CommentDTO(
        String parentId,
        String articleId,
        String content
) { }
