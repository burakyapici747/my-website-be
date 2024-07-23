package com.blog.mywebsite.api;

import com.blog.mywebsite.api.output.UserOutput;
import com.blog.mywebsite.api.request.UserCreateRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.mapper.UserMapper;
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
    public ResponseEntity<BaseResponse<UserOutput>> getUserById(
            @RequestParam(value = "id", required = false)
            @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ID_SIZE_MESSAGE)
            String id
    ){
        BaseResponse<UserOutput> response = new BaseResponse<>(
                null,
                UserMapper.INSTANCE.userDTOToUserOutput(userService.getById(id))
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/currentUser")
    public ResponseEntity<BaseResponse<UserOutput>> getCurrentUser(){
        BaseResponse<UserOutput> response = new BaseResponse<>(
                null,
                UserMapper.INSTANCE.userDTOToUserOutput(userService.getCurrentUser())
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<String>> create(@RequestBody UserCreateRequest userCreateRequest){
        BaseResponse<String> response = new BaseResponse<>(null, userService.create(userCreateRequest));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/self")
    public ResponseEntity<BaseResponse<UserOutput>> deleteCurrentUser(){
        BaseResponse<UserOutput> response = new BaseResponse<>(
                null,
                UserMapper.INSTANCE.userDTOToUserOutput(userService.deleteCurrentUser())
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<UserOutput>> deleteById(
            @RequestParam("id")
            @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ID_SIZE_MESSAGE)
            String id
    ){
        BaseResponse<UserOutput> response = new BaseResponse<>(
                null,
                UserMapper.INSTANCE.userDTOToUserOutput(userService.deleteById(id))
        );
        return ResponseEntity.ok(response);
    }
}