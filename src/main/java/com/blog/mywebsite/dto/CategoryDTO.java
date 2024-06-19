package com.blog.mywebsite.dto;

import java.util.List;

public record CategoryDTO(
        String id,
        String parentId,
        String name,
        List<CategoryDTO> subCategories
) {}