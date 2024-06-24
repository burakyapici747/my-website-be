package com.blog.mywebsite.api;

import com.blog.mywebsite.api.request.CommentPostRequest;
import com.blog.mywebsite.api.request.CommentPutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.CommentDTO;
import com.blog.mywebsite.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.blog.mywebsite.constant.ValidationConstant.*;
import static com.blog.mywebsite.constant.APIConstant.*;

import java.util.List;

@RestController
@RequestMapping(COMMENT_URL)
@Validated
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(COMMENTS_URL)
    public ResponseEntity<BaseResponse<List<CommentDTO>>> getComments(
            @RequestParam(value = "id", required = false)
            @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ID_SIZE_MESSAGE)
            String id,
            @RequestParam(value = "parent_id", required = false)
            @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = COMMENT_PARENT_ID_SIZE_MESSAGE)
            String parentId,
            BindingResult bindingResult
    ){
        BaseResponse<List<CommentDTO>> response = commentService.getComments(id, parentId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<CommentDTO>> create(
            @RequestBody @Valid CommentPostRequest commentPostRequest,
            BindingResult bindingResult
    ){
        BaseResponse<CommentDTO> response = commentService.create(commentPostRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<BaseResponse<CommentDTO>> updateById(
            @RequestParam(value = "id")
            @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ID_SIZE_MESSAGE)
            String id,
            @RequestBody @Valid CommentPutRequest commentPutRequest,
            BindingResult bindingResult
    ){
        BaseResponse<CommentDTO> response = commentService.updateById(id, commentPutRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> deleteById(
            @RequestParam("id")
            @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ID_SIZE_MESSAGE)
            String id,
            BindingResult bindingResult
    ){
        BaseResponse<Void> response = commentService.deleteById(id);
        return ResponseEntity.ok(response);
    }
}
