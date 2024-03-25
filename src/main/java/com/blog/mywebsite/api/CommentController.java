package com.blog.mywebsite.api;

import com.blog.mywebsite.api.request.CommentPostRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.CommentDTO;
import com.blog.mywebsite.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
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

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CommentDTO>> getById(@PathVariable("id") final String id){
        final BaseResponse<CommentDTO> response = commentService.getById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<CommentDTO>> create(@RequestBody CommentPostRequest commentPostRequest){
        final BaseResponse<CommentDTO> response = commentService.create(commentPostRequest);

        return ResponseEntity.ok(response);
    }
}
