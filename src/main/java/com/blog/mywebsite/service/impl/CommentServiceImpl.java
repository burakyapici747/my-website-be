package com.blog.mywebsite.service.impl;

import com.blog.mywebsite.api.request.CommentPostRequest;
import com.blog.mywebsite.api.request.CommentPutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.api.response.DataResponse;
import com.blog.mywebsite.api.response.SuccessDataResponse;
import com.blog.mywebsite.api.response.SuccessResponse;
import com.blog.mywebsite.constant.EntityConstant;
import com.blog.mywebsite.dto.CommentDTO;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.mapper.CommentMapper;
import com.blog.mywebsite.model.Comment;
import com.blog.mywebsite.repository.CommentRepository;
import com.blog.mywebsite.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public DataResponse<List<CommentDTO>> getAll(){
        final List<Comment> commentList = commentRepository.findAll();
        final List<CommentDTO> commentDTOList = CommentMapper.INSTANCE.commentListToCommentDTOList(commentList);

        return new SuccessDataResponse<>(commentDTOList, EntityConstant.SUCCESS_FETCH);
    }

    @Override
    public DataResponse<CommentDTO> getById(String id) {
        final Comment comment = findById(id);
        final CommentDTO commentDTO = CommentMapper.INSTANCE.commentToCommentDTO(comment);

        return new SuccessDataResponse<>(commentDTO, EntityConstant.SUCCESS_FETCH);
    }

    @Override
    public DataResponse<List<CommentDTO>> getAllByArticleId(String articleId) {
        final List<Comment> commentList = commentRepository.findAllByArticleId(UUID.fromString(articleId));
        final List<CommentDTO> commentDTOList = CommentMapper.INSTANCE.commentListToCommentDTOList(commentList);

        return new SuccessDataResponse<>(commentDTOList, EntityConstant.SUCCESS_FETCH);
    }

    @Override
    public BaseResponse deleteById(String id) {
        final Comment comment = findById(id);

        commentRepository.delete(comment);

        return new SuccessResponse(EntityConstant.SUCCESS_DELETE);
    }

    @Override
    public DataResponse<CommentDTO> updateById(String id, CommentPutRequest commentUpdateRequest) {
        final Comment comment = findById(id);

        comment.setContent(commentUpdateRequest.content());
        comment.setRate(commentUpdateRequest.rate());

        final CommentDTO commentDTO = CommentMapper.INSTANCE.commentToCommentDTO(commentRepository.save(comment));

        return new SuccessDataResponse<>(commentDTO, EntityConstant.SUCCESS_UPDATE);
    }

    @Override
    public DataResponse<CommentDTO> create(CommentPostRequest commentPostRequest) {
        final Comment comment = new Comment();

        comment.setContent(commentPostRequest.content());
        comment.setRate(commentPostRequest.rate());

        final CommentDTO commentDTO = CommentMapper.INSTANCE.commentToCommentDTO(commentRepository.save(comment));

        return new SuccessDataResponse<>(commentDTO, EntityConstant.SUCCESS_CREATE);
    }

    protected Comment findById(String id){
        return commentRepository.findById(UUID.fromString(id))
                .orElseThrow( () -> new EntityNotFoundException(EntityConstant.COMMENT_NOT_FOUND));
    }
}
