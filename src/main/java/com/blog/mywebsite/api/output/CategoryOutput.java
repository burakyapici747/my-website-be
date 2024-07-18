package com.blog.mywebsite.api.output;

import java.util.List;

public record CategoryOutput(
        String type,
        Attributes attributes
){
    public record Attributes(
            String id,
            String parentId,
            String name,
            List<CategoryOutput> subCategories
    ){}
}