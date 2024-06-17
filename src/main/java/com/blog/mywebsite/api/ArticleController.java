package com.blog.mywebsite.api;

import com.blog.mywebsite.api.request.ArticlePostRequest;
import com.blog.mywebsite.api.request.ArticlePutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.enumerator.SearchOperation;
import com.blog.mywebsite.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/article")
@Validated
public class ArticleController{
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/grouped-by-year")
    public ResponseEntity<BaseResponse<Map<Integer, List<ArticleDTO>>>> getByGroupedByYear(
            @RequestParam("publishDate") LocalDate publishDate,
            @RequestParam("searchOperation") SearchOperation searchOperation
    ){
        final BaseResponse<Map<Integer, List<ArticleDTO>>> response =
                articleService.getGroupedArticlesByYear(publishDate, searchOperation);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/grouped-by-year-category-name")
    public ResponseEntity<BaseResponse<Map<Integer, List<ArticleDTO>>>> getGroupedYearByCategoryName(
            @RequestParam String categoryName
    ){
        final BaseResponse<Map<Integer, List<ArticleDTO>>> response =
                articleService.getGroupedYearByCategoryName(categoryName);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/between-date")
    public ResponseEntity<BaseResponse<List<ArticleDTO>>> getByDateRange(
            @RequestParam("start-date") LocalDate startDate, @RequestParam("end-date") LocalDate endDate
    ){
        final BaseResponse<List<ArticleDTO>> response = articleService.getByDateRange(startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<ArticleDTO>>> getAll(){
        final BaseResponse<List<ArticleDTO>> response = articleService.getAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<ArticleDTO>> create(
            @Valid @RequestBody ArticlePostRequest articlePostRequest,
            BindingResult bindingResult
    ){
        final BaseResponse<ArticleDTO> response = articleService.create(articlePostRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<ArticleDTO>> update(
            @PathVariable String id, @Valid @RequestBody ArticlePutRequest articlePutRequest,
            BindingResult bindingResult
    ){
        final BaseResponse<ArticleDTO> response = articleService.updateById(id, articlePutRequest);
        return ResponseEntity.ok(response);
    }
}