package com.blog.mywebsite.service;

import com.blog.mywebsite.api.request.CommentPostRequest;
import com.blog.mywebsite.api.request.CommentPutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.api.response.DataResponse;
import com.blog.mywebsite.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    DataResponse<List<CommentDTO>> getAll();
    DataResponse<CommentDTO> getById(String id);
    BaseResponse deleteById(String id);
    DataResponse<CommentDTO> updateById(String id, CommentPutRequest commentUpdateRequest);
    DataResponse<CommentDTO> create(CommentPostRequest commentPostRequest);
}