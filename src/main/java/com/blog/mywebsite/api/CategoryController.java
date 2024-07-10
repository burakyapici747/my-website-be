package com.blog.mywebsite.api;

import com.blog.mywebsite.api.output.CategoryOutput;
import com.blog.mywebsite.api.request.CategoryGetRequest;
import com.blog.mywebsite.api.request.CategoryPostRequest;
import com.blog.mywebsite.api.request.CategoryPutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.mapper.CategoryMapper;
import com.blog.mywebsite.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@Validated
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<BaseResponse<List<CategoryOutput>>> getCategories(
            @ModelAttribute @Valid CategoryGetRequest categoryGetRequest
    ){
        BaseResponse<List<CategoryOutput>> response = new BaseResponse<>(
                null,
                CategoryMapper.INSTANCE.categoryDTOListToCategoryOutputList(
                        categoryService.getCategories(
                                categoryGetRequest.id(),
                                categoryGetRequest.parentId(),
                                categoryGetRequest.name()
                        )
                )
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<CategoryOutput>> create(
            @RequestBody
            @Valid
            CategoryPostRequest categoryPostRequest
    ){
        BaseResponse<CategoryOutput> response = new BaseResponse<>(
                null,
                CategoryMapper.INSTANCE.categoryDTOToCategoryOutput(categoryService.create(categoryPostRequest))
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<BaseResponse<CategoryOutput>> updateById(
            @RequestParam("id")
            @Size(min = 36, max = 36, message = "Id field must be empty or 36 characters long.")
            String id,
            @RequestBody @Valid CategoryPutRequest categoryPutRequest
    ) {
        BaseResponse<CategoryOutput> response = new BaseResponse<>(
                null,
                CategoryMapper.INSTANCE.categoryDTOToCategoryOutput(categoryService.updateById(id, categoryPutRequest))
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<CategoryOutput>> deleteById(
            @RequestParam("id")
            @Size(min = 36, max = 36, message = "Id field must be empty or 36 characters long.")
            String id
    ){
        BaseResponse<CategoryOutput> response = new BaseResponse<>(
                null,
                CategoryMapper.INSTANCE.categoryDTOToCategoryOutput(categoryService.deleteById(id))
        );
        return ResponseEntity.ok(response);
    }
}