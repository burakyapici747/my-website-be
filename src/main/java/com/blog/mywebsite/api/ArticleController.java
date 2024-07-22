package com.blog.mywebsite.api;

import com.blog.mywebsite.api.input.article.*;
import com.blog.mywebsite.api.output.ArticleOutput;
import com.blog.mywebsite.api.output.CommentOutput;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.mapper.ArticleMapper;
import com.blog.mywebsite.mapper.CommentMapper;
import com.blog.mywebsite.service.ArticleService;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.blog.mywebsite.constant.ValidationConstant.*;
import static com.blog.mywebsite.constant.APIConstant.*;
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
    public ResponseEntity<BaseResponse<List<ArticleOutput>>> getArticles(@Valid ArticleGetInput articleGetInput){
        BaseResponse<List<ArticleOutput>> response = new BaseResponse<>(
                null,
                ArticleMapper.INSTANCE.articleDTOListToArticleOutputList(articleService.getArticles(
                        articleGetInput.id(),
                        articleGetInput.title(),
                        articleGetInput.publishDate(),
                        articleGetInput.readingTime()
                ))
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<BaseResponse<List<ArticleOutput>>> getByDateRange(
            @Valid ArticleGetByDateRangeInput articleGetByDateRangeInput
    ) {
        BaseResponse<List<ArticleOutput>> response = new BaseResponse<>(
                null,
                ArticleMapper.INSTANCE.articleDTOListToArticleOutputList(
                        articleService.getByDateRange(articleGetByDateRangeInput.from(), articleGetByDateRangeInput.to())
                )
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/grouped-by-year")
    public ResponseEntity<BaseResponse<Map<Integer, List<ArticleOutput>>>> getGroupedByYear(
            @Valid ArticleGetGroupedByYearInput articleGetGroupedByYearInput
    ) {
        BaseResponse<Map<Integer, List<ArticleOutput>>> response = new BaseResponse<>(
                null,
                ArticleMapper.INSTANCE.toArticleOutputMap(
                        articleService.getGroupedArticlesByYear(
                                articleGetGroupedByYearInput.publishDate(),
                                articleGetGroupedByYearInput.searchOperation()
                        )
                )
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-category")
    public ResponseEntity<BaseResponse<Map<Integer, List<ArticleOutput>>>> getByCategoryNameGroupedByYear(
            @RequestParam(value = "categoryName", required = false) String categoryName
    ){
        BaseResponse<Map<Integer, List<ArticleOutput>>> response = new BaseResponse<>(
                null,
                ArticleMapper.INSTANCE.toArticleOutputMap(
                        articleService.getGroupedYearByCategoryName(categoryName)
                )
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("comments")
    public ResponseEntity<BaseResponse<List<CommentOutput>>> getComments(
            @RequestParam(value = "id")
            @Valid
            @Nullable
            @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ID_SIZE_MESSAGE)
            String id
    ){
        BaseResponse<List<CommentOutput>> response = new BaseResponse<>(
                null,
                CommentMapper.INSTANCE.commentDTOListToCommentOutputList(articleService.getComments(id))
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<ArticleOutput>> create(@RequestBody @Valid ArticlePostInput articlePostInput){
        BaseResponse<ArticleOutput> response = new BaseResponse<>(
                null,
                ArticleMapper.INSTANCE.articleDTOToArticleOutput(articleService.create(articlePostInput))
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<BaseResponse<ArticleOutput>> updateById(
            @RequestParam("id")
            @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ID_SIZE_MESSAGE)
            String id,
            @RequestBody ArticlePutInput articlePutInput
    ) {
        BaseResponse<ArticleOutput> response = new BaseResponse<>(
                null,
                ArticleMapper.INSTANCE.articleDTOToArticleOutput(articleService.updateById(id, articlePutInput))
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