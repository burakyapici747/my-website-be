package com.blog.mywebsite.service;

import com.blog.mywebsite.api.request.CommentPostRequest;
import com.blog.mywebsite.api.request.CommentPutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    BaseResponse<List<CommentDTO>> getAll();
    BaseResponse<CommentDTO> getById(String id);
    BaseResponse<Void> deleteById(String id);
    BaseResponse<CommentDTO> updateById(String id, CommentPutRequest commentUpdateRequest);
    BaseResponse<CommentDTO> create(CommentPostRequest commentPostRequest);
}