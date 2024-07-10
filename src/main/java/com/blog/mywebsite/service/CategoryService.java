package com.blog.mywebsite.service;

import com.blog.mywebsite.api.request.CategoryPostRequest;
import com.blog.mywebsite.api.request.CategoryPutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.CategoryDTO;
import com.blog.mywebsite.model.Category;
import java.util.List;
import java.util.Map;

public interface CategoryService {
    List<CategoryDTO> getCategories(String id, String parentId, String name);
    Map<String, List<CategoryDTO>> getGroupedCategoriesWithSubcategories();
    CategoryDTO deleteById(String id);
    CategoryDTO create(CategoryPostRequest categoryPostRequest);
    CategoryDTO updateById(String id, CategoryPutRequest categoryPutRequest);
    Category findById(String id);
}
