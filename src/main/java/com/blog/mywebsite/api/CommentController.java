package com.blog.mywebsite.api;

import com.blog.mywebsite.api.request.CommentPostRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.CommentDTO;
import com.blog.mywebsite.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@Validated
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<CommentDTO>>> getAll(){
        final BaseResponse<List<CommentDTO>> response = commentService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-articles")
    public ResponseEntity<BaseResponse<List<CommentDTO>>> getArticles(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "parent_id", required = false) String parentId
    ){
        final BaseResponse<List<CommentDTO>> response = commentService.getComments(id, parentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CommentDTO>> getById(@PathVariable("id") final String id){
        final BaseResponse<CommentDTO> response = commentService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<CommentDTO>> create(
            @Valid @RequestBody CommentPostRequest commentPostRequest,
            BindingResult bindingResult
    ){
        final BaseResponse<CommentDTO> response = commentService.create(commentPostRequest);
        return ResponseEntity.ok(response);
    }
}
