package com.blog.mywebsite.api;

import com.blog.mywebsite.api.request.ArticlePostRequest;
import com.blog.mywebsite.api.request.ArticlePutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/article")
@Validated
public class ArticleController {
    //:TODO Client üzerinden gelen isteklerde, Body için: size, eleman adet vs. gibi kontroller eklenecek.
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ArticleDTO>> getById(@PathVariable("id") final String id){
        final BaseResponse<ArticleDTO> response = articleService.getById(id);

        return ResponseEntity.ok(response);
    }

//    @GetMapping("/year/{year}")
//    public ResponseEntity<BaseResponse<List<ArticleDTO>>> getByYear(@PathVariable int year){
//        final BaseResponse<List<ArticleDTO>> response = articleService.getByYear(year);
//
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/date/{date}")
    public ResponseEntity<BaseResponse<List<ArticleDTO>>> getByDate(@PathVariable LocalDate date){
        final BaseResponse<List<ArticleDTO>> response = articleService.getByDate(date);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/between-date/{startDate}/{endDate}")
    public ResponseEntity<BaseResponse<List<ArticleDTO>>> getByDateRange(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate){
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