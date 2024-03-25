package com.blog.mywebsite.api;

import com.blog.mywebsite.api.request.UserCreateRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/user")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<String>> create(@RequestBody UserCreateRequest userCreateRequest){
        return ResponseEntity.ok(userService.create(userCreateRequest));
    }
}