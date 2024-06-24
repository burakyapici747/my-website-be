package com.blog.mywebsite.api;

import com.blog.mywebsite.api.request.UserCreateRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.UserDTO;
import com.blog.mywebsite.service.UserService;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.blog.mywebsite.constant.ValidationConstant.*;
import static com.blog.mywebsite.constant.APIConstant.*;

@RestController
@RequestMapping(USER_URL)
@Validated
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<BaseResponse<UserDTO>> getUserById(
            @RequestParam(value = "id", required = false)
            @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ID_SIZE_MESSAGE)
            String id
    ){
        BaseResponse<UserDTO> response = userService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<String>> create(@RequestBody UserCreateRequest userCreateRequest){
        return ResponseEntity.ok(userService.create(userCreateRequest));
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> deleteById(
            @RequestParam("id")
            @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ID_SIZE_MESSAGE)
            String id
    ){
        BaseResponse<Void> response = userService.deleteById(id);
        return ResponseEntity.ok(response);
    }
}