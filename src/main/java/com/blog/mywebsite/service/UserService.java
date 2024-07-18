package com.blog.mywebsite.service;

import com.blog.mywebsite.api.request.UserCreateRequest;
import com.blog.mywebsite.dto.UserDTO;
import com.blog.mywebsite.model.User;

public interface UserService {
    UserDTO getById(String id);
    String create(UserCreateRequest userCreateRequest);
    UserDTO deleteById(String id);
    UserDTO getCurrentUser();
    UserDTO updateNameById(String id, String name);
    UserDTO deleteCurrentUser();
}