package com.blog.mywebsite.service;

import com.blog.mywebsite.api.request.CategoryPostRequest;
import com.blog.mywebsite.api.request.CategoryPutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.CategoryDTO;
import com.blog.mywebsite.model.Category;
import java.util.List;
import java.util.Map;

public interface CategoryService {
    BaseResponse<List<CategoryDTO>> getAll();
    BaseResponse<List<CategoryDTO>> getCategories(String id, String parentId, String name);
    BaseResponse<Map<String, List<CategoryDTO>>> getGroupedCategoriesWithSubcategories();
    BaseResponse<Void> deleteById(String id);
    BaseResponse<CategoryDTO> create(CategoryPostRequest categoryPostRequest);
    BaseResponse<CategoryDTO> updateById(String id, CategoryPutRequest categoryPutRequest);
    Category findById(String id);
}
