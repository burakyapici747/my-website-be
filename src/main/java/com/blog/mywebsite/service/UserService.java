package com.blog.mywebsite.service;

import com.blog.mywebsite.api.request.UserCreateRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.UserDTO;

public interface UserService {
    BaseResponse<UserDTO> getById(String id);
    BaseResponse<String> create(UserCreateRequest userCreateRequest);
    BaseResponse<Void> deleteById(String id);
    BaseResponse<UserDTO> updateNameById(String id, String name);
    BaseResponse<Void> deleteCurrentUser();
}