package com.blog.mywebsite.service.impl;

import com.blog.mywebsite.api.request.UserCreateRequest;
import com.blog.mywebsite.api.response.*;
import com.blog.mywebsite.constant.EntityConstant;
import com.blog.mywebsite.dto.UserDTO;
import com.blog.mywebsite.exception.EntityExistException;
import com.blog.mywebsite.mapper.UserMapper;
import com.blog.mywebsite.model.Role;
import com.blog.mywebsite.model.User;
import com.blog.mywebsite.repository.RoleRepository;
import com.blog.mywebsite.repository.UserRepository;
import com.blog.mywebsite.service.UserService;
import com.blog.mywebsite.common.util.security.JWTHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public BaseResponse<UserDTO> getById(String id){
        User user = findById(id);
        UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(user);
        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, userDTO);
    }

    @Override
    public BaseResponse<String> create(UserCreateRequest userCreateRequest) {
        checkUserIsExistByEmail(userCreateRequest.email());

        String jwtToken = JWTHelper.generateJwtToken(
                userCreateRequest.email(),
                List.of(com.blog.mywebsite.enumerator.Role.USER.getValue())
        );

        Role role = getRoleForUser();
        User user = new User();
        user.setName(userCreateRequest.name());
        user.setEmail(userCreateRequest.email());
        user.getRoles().add(role);

        userRepository.save(user);

        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_CREATE, jwtToken);
    }

    @Override
    public BaseResponse<UserDTO> updateNameById(String id, String name) {
        User user = findById(id);
        user.setName(name);

        userRepository.save(user);
        UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(user);
        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_UPDATE, userDTO);
    }
    @Override
    public BaseResponse<Void> deleteById(String id) {
        User user = findById(id);
        userRepository.delete(user);
        return new SuccessfulResponse(HttpStatus.OK.value(), EntityConstant.SUCCESS_DELETE);
    }

    private void checkUserIsExistByEmail(String email){
        if(userRepository.existsByEmail(email)){
            throw new EntityExistException("This email address (" + email + ") is already in use.");
        }
    }

    private User findById(String id){
        return userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(EntityConstant.NOT_FOUND_DATA));
    }

    protected User getUserByEmail(String email){
         return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(EntityConstant.NOT_FOUND_DATA));
    }

    private Role getRoleForUser(){
        return roleRepository.findByName(com.blog.mywebsite.enumerator.Role.USER.getValue())
                .orElseThrow(() -> new EntityNotFoundException(EntityConstant.NOT_FOUND_DATA));
    }
}