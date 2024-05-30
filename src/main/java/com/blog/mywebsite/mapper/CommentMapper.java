package com.blog.mywebsite.mapper;

import com.blog.mywebsite.dto.CommentDTO;
import com.blog.mywebsite.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    CommentDTO commentToCommentDTO(Comment comment);
    List<CommentDTO> commentsToCommentDTOs(List<Comment> comments);
}
