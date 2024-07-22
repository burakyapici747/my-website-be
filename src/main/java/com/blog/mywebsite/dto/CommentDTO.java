package com.blog.mywebsite.dto;

public record CommentDTO(
        String id,
        String parentId,
        String content
) { }
