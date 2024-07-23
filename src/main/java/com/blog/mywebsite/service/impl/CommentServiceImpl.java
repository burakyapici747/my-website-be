package com.blog.mywebsite.service.impl;

import com.blog.mywebsite.api.input.comment.CommentPostInput;
import com.blog.mywebsite.api.input.comment.CommentPutInput;
import com.blog.mywebsite.common.util.ValueUtil;
import com.blog.mywebsite.constant.EntityConstant;
import com.blog.mywebsite.dto.CommentDTO;
import com.blog.mywebsite.enumerator.SearchOperation;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.mapper.CommentMapper;
import com.blog.mywebsite.model.Article;
import com.blog.mywebsite.model.Comment;
import com.blog.mywebsite.repository.CommentRepository;
import com.blog.mywebsite.service.ArticleService;
import com.blog.mywebsite.service.CommentService;
import com.blog.mywebsite.specification.CommonSpecification;
import com.blog.mywebsite.specification.SearchCriteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.blog.mywebsite.constant.CommentConstant.*;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ArticleService articleService;

    public CommentServiceImpl(CommentRepository commentRepository, ArticleService articleService) {
        this.commentRepository = commentRepository;
        this.articleService = articleService;
    }

    @Override
    public List<CommentDTO> getComments(String id, String parentId) {
        CommonSpecification<Comment> specification = new CommonSpecification<>();
        specification.add(new SearchCriteria(ID, id, SearchOperation.EQUAL));
        specification.add(new SearchCriteria(PARENT_ID, parentId, SearchOperation.EQUAL));

        List<Comment> commentList = commentRepository.findAll(specification);
        return CommentMapper.INSTANCE.commentsToCommentDTOs(commentList);
    }

    @Override
    public CommentDTO deleteById(String id) {
        Comment comment = findById(id);
        commentRepository.delete(comment);
        return CommentMapper.INSTANCE.commentToCommentDTO(comment);
    }

    @Override
    public CommentDTO updateById(String id, CommentPutInput commentPutInput) {
        Comment comment = findById(id);
        comment.setContent(commentPutInput.content());
        return CommentMapper.INSTANCE.commentToCommentDTO(commentRepository.save(comment));
    }

    @Override
    public CommentDTO create(CommentPostInput commentPostInput) {
        ValueUtil.checkDataIsNull(commentPostInput, "CommentPostInput is can not be null.");

        Article article = articleService.findById(commentPostInput.articleId());
        Comment comment = new Comment();
        comment.setContent(commentPostInput.content());
        article.getComments().add(comment);
        checkParentCommentExistThenSetParentComment(commentPostInput.parentId(), comment);
        return CommentMapper.INSTANCE.commentToCommentDTO(commentRepository.save(comment));
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