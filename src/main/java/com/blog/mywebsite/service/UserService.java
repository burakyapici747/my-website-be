package com.blog.mywebsite.service;

import com.blog.mywebsite.api.request.UserCreateRequest;
import com.blog.mywebsite.dto.UserDTO;

public interface UserService {
    DataResponse<UserDTO> getById(String id);
    DataResponse<String> create(UserCreateRequest userCreateRequest);
    BaseResponse deleteById(String id);
    DataResponse<UserDTO> updateNameById(String id, String name);
}
