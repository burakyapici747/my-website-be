package com.blog.mywebsite.api;

import com.blog.mywebsite.api.request.ArticlePostRequest;
import com.blog.mywebsite.api.response.DataResponse;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@Validated
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<ArticleDTO>>> getAll(){
        final DataResponse<List<ArticleDTO>> response = articleService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<ArticleDTO>> getById(@PathVariable("id") final String id){
        final DataResponse<ArticleDTO> response = articleService.getById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<DataResponse<ArticleDTO>> create(@Valid @RequestBody ArticlePostRequest articlePostRequest, BindingResult bindingResult){
        final DataResponse<ArticleDTO> response = articleService.create(articlePostRequest);

        return ResponseEntity.ok(response);
    }
}