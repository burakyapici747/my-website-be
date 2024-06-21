package com.blog.mywebsite.service.impl;

import com.blog.mywebsite.api.request.CommentPostRequest;
import com.blog.mywebsite.api.request.CommentPutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.api.response.SuccessfulResponse;
import com.blog.mywebsite.api.response.SuccessfulDataResponse;
import com.blog.mywebsite.common.util.ValueUtil;
import com.blog.mywebsite.constant.EntityConstant;
import com.blog.mywebsite.dto.CommentDTO;
import com.blog.mywebsite.enumerator.SearchOperation;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.mapper.CommentMapper;
import com.blog.mywebsite.model.Comment;
import com.blog.mywebsite.repository.CommentRepository;
import com.blog.mywebsite.service.CommentService;
import com.blog.mywebsite.specification.CommonSpecification;
import com.blog.mywebsite.specification.SearchCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.blog.mywebsite.constant.CommentConstant.*;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public BaseResponse<List<CommentDTO>> getComments(String id, String parentId) {
        CommonSpecification<Comment> specification = new CommonSpecification<>();
        specification.add(new SearchCriteria(ID, id, SearchOperation.EQUAL));
        specification.add(new SearchCriteria(PARENT_ID, parentId, SearchOperation.EQUAL));

        List<Comment> commentList = commentRepository.findAll(specification);
        List<CommentDTO> commentDTOList = CommentMapper.INSTANCE.commentsToCommentDTOs(commentList);
        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, commentDTOList);
    }

    @Override
    public BaseResponse<Void> deleteById(String id) {
        Comment comment = findById(id);

        commentRepository.delete(comment);
        return new SuccessfulResponse(HttpStatus.OK.value(), EntityConstant.SUCCESS_DELETE);
    }

    @Override
    public BaseResponse<CommentDTO> updateById(String id, CommentPutRequest commentUpdateRequest) {
        Comment comment = findById(id);
        comment.setContent(commentUpdateRequest.content());

        CommentDTO commentDTO = CommentMapper.INSTANCE.commentToCommentDTO(commentRepository.save(comment));
        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_UPDATE, commentDTO);
    }

    @Override
    public BaseResponse<CommentDTO> create(CommentPostRequest commentPostRequest) {
        ValueUtil.checkDataIsNull(commentPostRequest, "CommentPostRequest is can not be null.");

        Comment comment = new Comment();
        comment.setContent(commentPostRequest.content());
        checkParentCommentExistThenSetParentComment(commentPostRequest.parentId(), comment);

        CommentDTO commentDTO = CommentMapper.INSTANCE.commentToCommentDTO(commentRepository.save(comment));
        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_CREATE, commentDTO);
    }

    private void checkParentCommentExistThenSetParentComment(String parentId, Comment comment){
        if(Objects.nonNull(parentId)){
            comment.setParent(findById(parentId));
        }
    }

    protected Comment findById(String id){
        return commentRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException(EntityConstant.COMMENT_NOT_FOUND));
    }
}