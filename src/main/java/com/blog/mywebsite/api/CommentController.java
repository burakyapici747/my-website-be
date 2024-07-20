package com.blog.mywebsite.api;

import com.blog.mywebsite.api.input.comment.CommentGetInput;
import com.blog.mywebsite.api.input.comment.CommentPostInput;
import com.blog.mywebsite.api.input.comment.CommentPutInput;
import com.blog.mywebsite.api.output.CommentOutput;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.mapper.CommentMapper;
import com.blog.mywebsite.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BaseResponse<List<CommentOutput>>> getComments(@Valid CommentGetInput commentGetInput){
        BaseResponse<List<CommentOutput>> response = new BaseResponse<>(
                null,
                CommentMapper.INSTANCE.commentDTOListToCommentOutputList(
                        commentService.getComments(commentGetInput.id(), commentGetInput.parentId())
                )
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<CommentOutput>> create(@RequestBody @Valid CommentPostInput commentPostInput){
        BaseResponse<CommentOutput> response = new BaseResponse<>(
                null,
                CommentMapper.INSTANCE.commentDTOToCommentOutput(commentService.create(commentPostInput))
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<BaseResponse<CommentOutput>> updateById(
            @RequestParam(value = "id")
            @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ID_SIZE_MESSAGE)
            String id,
            @RequestBody @Valid CommentPutInput commentPutInput
    ){
        BaseResponse<CommentOutput> response = new BaseResponse<>(
                null,
                CommentMapper.INSTANCE.commentDTOToCommentOutput(commentService.updateById(id, commentPutInput))
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<CommentOutput>> deleteById(
            @RequestParam("id")
            @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ID_SIZE_MESSAGE)
            String id
    ){
        BaseResponse<CommentOutput> response = new BaseResponse<>(
                null,
                CommentMapper.INSTANCE.commentDTOToCommentOutput(commentService.deleteById(id))
        );
        return ResponseEntity.ok(response);
    }
}
