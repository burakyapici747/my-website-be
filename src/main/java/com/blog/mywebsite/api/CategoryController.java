package com.blog.mywebsite.api;

import com.blog.mywebsite.api.request.CategoryPostRequest;
import com.blog.mywebsite.api.request.CategoryPutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.CategoryDTO;
import com.blog.mywebsite.service.CategoryService;
import jakarta.validation.Valid;
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

    @GetMapping
    public ResponseEntity<BaseResponse<List<CategoryDTO>>> getAll(){
        final BaseResponse<List<CategoryDTO>> response = categoryService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories")
    public ResponseEntity<BaseResponse<List<CategoryDTO>>> getCategories(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "parent_id", required = false) String parentId,
            @RequestParam(value = "name", required = false) String name
    ){
        final BaseResponse<List<CategoryDTO>> response = categoryService.getCategories(id, parentId, name);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<CategoryDTO>> create(@RequestBody @Valid CategoryPostRequest categoryPostRequest){
        final BaseResponse<CategoryDTO> response = categoryService.create(categoryPostRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<BaseResponse<CategoryDTO>> updateById(
            @RequestParam("id") String id,
            @RequestBody @Valid CategoryPutRequest categoryPutRequest
    ) {
        final BaseResponse<CategoryDTO> response = categoryService.updateById(id, categoryPutRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> deleteById(@RequestParam("id") String id){
        final BaseResponse<Void> response = categoryService.deleteById(id);
        return ResponseEntity.ok(response);
    }
}