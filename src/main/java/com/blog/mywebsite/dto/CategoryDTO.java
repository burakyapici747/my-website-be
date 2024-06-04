package com.blog.mywebsite.dto;

import java.util.List;
import java.util.Set;

public record CategoryDTO(
        String id,
        String parentId,
        String name,
        List<CategoryDTO> subCategories
) {}