package com.blog.mywebsite.api.output;

public record CommentOutput(
        String type,
        Attributes attributes
){
    public record Attributes(
            String id,
            String parentId,
            String content
    ){}
}