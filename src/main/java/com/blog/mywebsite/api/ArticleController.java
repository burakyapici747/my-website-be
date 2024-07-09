package com.blog.mywebsite.api;

import com.blog.mywebsite.api.output.ArticleOutput;
import com.blog.mywebsite.api.request.ArticleGetByDateRange;
import com.blog.mywebsite.api.request.ArticleGetRequest;
import com.blog.mywebsite.api.request.ArticlePostRequest;
import com.blog.mywebsite.api.request.ArticlePutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.enumerator.SearchOperation;
import com.blog.mywebsite.mapper.ArticleMapper;
import com.blog.mywebsite.service.ArticleService;
import com.blog.mywebsite.validation.ISO8601Validation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.blog.mywebsite.constant.ValidationConstant.*;
import static com.blog.mywebsite.constant.APIConstant.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ARTICLE_URL)
@Validated
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }
    @GetMapping("/articles")
    public ResponseEntity<BaseResponse<List<ArticleOutput>>> getArticles(
            @ModelAttribute @Valid ArticleGetRequest articleGetRequest
    ) {
        BaseResponse<List<ArticleOutput>> response = new BaseResponse<>(
                null,
                ArticleMapper.INSTANCE.articleDTOListToArticleOutputList(articleService.getArticles(
                        articleGetRequest.id(),
                        articleGetRequest.categoryId(),
                        articleGetRequest.publishDate(),
                        articleGetRequest.readingTime()
                ))
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<BaseResponse<List<ArticleOutput>>> getByDateRange(
            @Valid ArticleGetByDateRange articleGetByDateRange
    ) {
        BaseResponse<List<ArticleOutput>> response = new BaseResponse<>(
                null,
                ArticleMapper.INSTANCE.articleDTOListToArticleOutputList(
                        articleService.getByDateRange(articleGetByDateRange.from(), articleGetByDateRange.to())
                )
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/grouped-by-year")
    public ResponseEntity<BaseResponse<Map<Integer, List<ArticleOutput>>>> getGroupedByYear(
            @RequestParam("publishDate")
            @ISO8601Validation
            LocalDate publishDate,
            @RequestParam("searchOperation") SearchOperation searchOperation
    ) {
        BaseResponse<Map<Integer, List<ArticleOutput>>> response = new BaseResponse<>(
                null,
                ArticleMapper.INSTANCE.toArticleOutputMap(articleService.getGroupedArticlesByYear(publishDate, searchOperation))
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-category")
    public ResponseEntity<BaseResponse<Map<Integer, List<ArticleOutput>>>> getByCategoryNameGroupedByYear(
            @RequestParam("categoryName") String categoryName
    ) {
        BaseResponse<Map<Integer, List<ArticleOutput>>> response = new BaseResponse<>(
                null,
                ArticleMapper.INSTANCE.toArticleOutputMap(
                        articleService.getGroupedYearByCategoryName(categoryName)
                )
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<ArticleOutput>> create(
            @RequestBody @Valid ArticlePostRequest articlePostRequest,
            BindingResult bindingResult
    ) {
        BaseResponse<ArticleOutput> response = new BaseResponse<>(
                null, ArticleMapper.INSTANCE.articleDTOToArticleOutput(articleService.create(articlePostRequest))
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<BaseResponse<ArticleOutput>> updateById(
            @RequestParam("id")
            @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ID_SIZE_MESSAGE)
            String id,
            @RequestBody @Valid ArticlePutRequest articlePutRequest,
            BindingResult bindingResult
    ) {
        BaseResponse<ArticleOutput> response = new BaseResponse<>(
                null,
                ArticleMapper.INSTANCE.articleDTOToArticleOutput(articleService.updateById(id, articlePutRequest))
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<ArticleOutput>> deleteById(
            @RequestParam("id")
            @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ID_SIZE_MESSAGE)
            String id
    ) {
        BaseResponse<ArticleOutput> response = new BaseResponse<>(
                null,
                ArticleMapper.INSTANCE.articleDTOToArticleOutput(articleService.deleteById(id))
        );
        return ResponseEntity.ok(response);
    }
}