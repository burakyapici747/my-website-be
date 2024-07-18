package com.blog.mywebsite.service;

import com.blog.mywebsite.api.input.category.CategoryPostInput;
import com.blog.mywebsite.api.input.category.CategoryPutInput;
import com.blog.mywebsite.dto.CategoryDTO;
import com.blog.mywebsite.model.Category;
import java.util.List;
import java.util.Map;

public interface CategoryService {
    List<CategoryDTO> getCategories(String id, String parentId, String name);
    Map<String, List<CategoryDTO>> getGroupedCategoriesWithSubcategories();
    CategoryDTO deleteById(String id);
    CategoryDTO create(CategoryPostInput categoryPostInput);
    CategoryDTO updateById(String id, CategoryPutInput categoryPutInput);
    Category findById(String id);
}
