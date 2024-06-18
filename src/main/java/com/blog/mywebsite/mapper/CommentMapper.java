package com.blog.mywebsite.mapper;

import com.blog.mywebsite.dto.CommentDTO;
import com.blog.mywebsite.model.Comment;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    @Mapping(source = "parent.id", target = "parentId")
    CommentDTO commentToCommentDTO(Comment comment);
    List<CommentDTO> commentsToCommentDTOs(List<Comment> comments);
}