package com.blog.mywebsite.service;

import com.blog.mywebsite.api.request.CommentPostRequest;
import com.blog.mywebsite.api.request.CommentPutRequest;
import com.blog.mywebsite.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getComments(String id, String parentId);
    CommentDTO deleteById(String id);
    CommentDTO updateById(String id, CommentPutRequest commentUpdateRequest);
    CommentDTO create(CommentPostRequest commentPostRequest);
}