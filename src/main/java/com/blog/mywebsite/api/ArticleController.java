package com.blog.mywebsite.api;

import com.blog.mywebsite.api.request.ArticleGetRequest;
import com.blog.mywebsite.api.request.ArticlePostRequest;
import com.blog.mywebsite.api.request.ArticlePutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.enumerator.SearchOperation;
import com.blog.mywebsite.service.ArticleService;
import com.blog.mywebsite.validation.ISO8601Validation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
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

    @GetMapping("/articles")
    public ResponseEntity<BaseResponse<List<ArticleDTO>>> getArticles(
            @ModelAttribute @Valid ArticleGetRequest articleGetRequest,
            BindingResult bindingResult
    ){
        BaseResponse<List<ArticleDTO>> response = articleService.getArticles(
                articleGetRequest.id(),
                articleGetRequest.categoryId(),
                articleGetRequest.publishDate(),
                articleGetRequest.readingTime()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/between-date")
    public ResponseEntity<BaseResponse<List<ArticleDTO>>> getByDateRange(
            @RequestParam("start-date")
            @ISO8601Validation
            LocalDate startDate,
            @RequestParam("end-date")
            @ISO8601Validation
            LocalDate endDate,
            BindingResult bindingResult
    ){
        BaseResponse<List<ArticleDTO>> response = articleService.getByDateRange(startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/grouped-by-year")
    public ResponseEntity<BaseResponse<Map<Integer, List<ArticleDTO>>>> getByGroupedByYear(
            @RequestParam("publishDate")
            @ISO8601Validation
            LocalDate publishDate,
            @RequestParam("searchOperation") SearchOperation searchOperation
    ){
        BaseResponse<Map<Integer, List<ArticleDTO>>> response =
                articleService.getGroupedArticlesByYear(publishDate, searchOperation);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/grouped-by-year-category-name")
    public ResponseEntity<BaseResponse<Map<Integer, List<ArticleDTO>>>> getGroupedYearByCategoryName(
            @RequestParam("categoryName") String categoryName
    ){
        BaseResponse<Map<Integer, List<ArticleDTO>>> response =
                articleService.getGroupedYearByCategoryName(categoryName);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<ArticleDTO>> create(
            @RequestBody @Valid ArticlePostRequest articlePostRequest,
            BindingResult bindingResult
    ){
        BaseResponse<ArticleDTO> response = articleService.create(articlePostRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<BaseResponse<ArticleDTO>> updateById(
            @RequestParam("id")
            @Size(min = 36, max = 36, message = "Id field must be empty or 36 characters long.")
            String id,
            @RequestBody @Valid ArticlePutRequest articlePutRequest,
            BindingResult bindingResult
    ){
        BaseResponse<ArticleDTO> response = articleService.updateById(id, articlePutRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> deleteById(
            @RequestParam("id")
            @Size(min = 36, max = 36, message = "Id field must be empty or 36 characters long.")
            String id
    ){
        BaseResponse<Void> response = articleService.deleteById(id);
        return ResponseEntity.ok(response);
    }
}