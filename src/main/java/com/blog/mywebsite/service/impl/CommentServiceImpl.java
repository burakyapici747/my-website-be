package com.blog.mywebsite.service.impl;

import com.blog.mywebsite.api.request.CommentPostRequest;
import com.blog.mywebsite.api.request.CommentPutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.api.response.SuccessDataResponse;
import com.blog.mywebsite.api.response.SuccessResponse;
import com.blog.mywebsite.constant.EntityConstant;
import com.blog.mywebsite.dto.CommentDTO;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.mapper.CommentMapper;
import com.blog.mywebsite.model.Comment;
import com.blog.mywebsite.repository.CommentRepository;
import com.blog.mywebsite.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public BaseResponse<CommentDTO> create(CommentPostRequest commentPostRequest) {
        final Comment comment = new Comment();
        comment.setContent(commentPostRequest.content());
        comment.setRate(commentPostRequest.rate());

        final CommentDTO commentDTO = CommentMapper.INSTANCE.commentToCommentDTO(commentRepository.save(comment));

        return new SuccessDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_CREATE, commentDTO);
    }

    @Override
    public BaseResponse<List<CommentDTO>> getAll(){
        final List<Comment> comments = commentRepository.findAll();
        final List<CommentDTO> commentDTOs = CommentMapper.INSTANCE.commentsToCommentDTOs(comments);

        return new SuccessDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, commentDTOs);
    }

    @Override
    public BaseResponse<CommentDTO> getById(String id) {
        final Comment comment = findById(id);
        final CommentDTO commentDTO = CommentMapper.INSTANCE.commentToCommentDTO(comment);

        return new SuccessDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, commentDTO);
    }

    @Override
    public BaseResponse<CommentDTO> updateById(String id, CommentPutRequest commentUpdateRequest) {
        final Comment comment = findById(id);
        comment.setContent(commentUpdateRequest.content());
        comment.setRate(commentUpdateRequest.rate());

        final CommentDTO commentDTO = CommentMapper.INSTANCE.commentToCommentDTO(commentRepository.save(comment));

        return new SuccessDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_UPDATE, commentDTO);
    }

    @Override
    public BaseResponse<Void> deleteById(String id) {
        final Comment comment = findById(id);

        commentRepository.delete(comment);

        return new SuccessResponse(HttpStatus.OK.value(), EntityConstant.SUCCESS_DELETE);
    }

    @Override
    public BaseResponse<CommentDTO> addReplyToComment(String commentId, CommentPostRequest commentPostRequest){
        final Comment comment = new Comment();

        return null;
    }

    protected Comment findById(String id){
        return commentRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException(EntityConstant.COMMENT_NOT_FOUND));
    }
}