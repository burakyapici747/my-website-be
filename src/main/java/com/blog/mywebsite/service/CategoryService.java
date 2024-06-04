package com.blog.mywebsite.service;

import com.blog.mywebsite.api.request.CategoryPostRequest;
import com.blog.mywebsite.api.request.CategoryPutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import java.util.List;

public interface CategoryService {
    BaseResponse<List<CategoryDTO>> getAll();
    BaseResponse<List<CategoryDTO>> getCategories(String id, String parentId, String name);
    BaseResponse<Void> deleteById(String id);
    BaseResponse<CategoryDTO> create(CategoryPostRequest categoryPostRequest);
    BaseResponse<CategoryDTO> updateById(String id, CategoryPutRequest categoryPutRequest);
}
