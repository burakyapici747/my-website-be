package com.blog.mywebsite.service;

import com.blog.mywebsite.api.request.CommentPostRequest;
import com.blog.mywebsite.api.request.CommentPutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    BaseResponse<List<CommentDTO>> getComments(String id, String parentId);
    BaseResponse<Void> deleteById(String id);
    BaseResponse<CommentDTO> updateById(String id, CommentPutRequest commentUpdateRequest);
    BaseResponse<CommentDTO> create(CommentPostRequest commentPostRequest);
}