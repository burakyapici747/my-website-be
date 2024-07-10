package com.blog.mywebsite.api.output;

import java.time.LocalDateTime;

public record UserOutput(
        String type,
        Attributes attributes
){
    public record Attributes(
            String id,
            String email,
            String name,
            LocalDateTime createdAt
    ){}
}