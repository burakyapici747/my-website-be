package com.blog.mywebsite.service;

import com.blog.mywebsite.api.input.comment.CommentPostInput;
import com.blog.mywebsite.api.input.comment.CommentPutInput;
import com.blog.mywebsite.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getComments(String id, String parentId);
    CommentDTO deleteById(String id);
    CommentDTO updateById(String id, CommentPutInput commentPutInput);
    CommentDTO create(CommentPostInput commentPostInput);
}