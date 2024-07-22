package com.blog.mywebsite.service.impl;

import com.blog.mywebsite.api.request.UserCreateRequest;
import com.blog.mywebsite.common.util.security.JWTHelper;
import com.blog.mywebsite.common.util.security.SystemHelper;
import com.blog.mywebsite.constant.EntityConstant;
import com.blog.mywebsite.dto.UserDTO;
import com.blog.mywebsite.exception.EntityExistException;
import com.blog.mywebsite.mapper.UserMapper;
import com.blog.mywebsite.model.Role;
import com.blog.mywebsite.model.User;
import com.blog.mywebsite.repository.RoleRepository;
import com.blog.mywebsite.repository.UserRepository;
import com.blog.mywebsite.service.MailService;
import com.blog.mywebsite.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SystemHelper systemHelper;
    private final MailService mailService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, SystemHelper systemHelper, MailService mailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.systemHelper = systemHelper;
        this.mailService = mailService;
    }

    @Override
    public UserDTO getById(String id){
        User user = findById(id);
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @Override
    public UserDTO getCurrentUser(){
        User user = systemHelper.getCurrentUser();
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @Override
    public String create(UserCreateRequest userCreateRequest) {
        checkUserIsExistByEmail(userCreateRequest.email());

        String jwtToken = JWTHelper.generateJwtToken(
                userCreateRequest.email(),
                List.of(com.blog.mywebsite.enumerator.Role.ADMIN.getValue())
        );

        Role role = getRoleForUser();
        User user = new User();
        user.setName(userCreateRequest.name());
        user.setEmail(userCreateRequest.email());
        user.getRoles().add(role);

        userRepository.save(user);
        //mailService.sendEmail("burakyapici747@gmail.com", new String[]{userCreateRequest.email()}, "User Register Email", jwtToken);
        return jwtToken;
    }

    @Override
    public UserDTO updateNameById(String id, String name) {
        User user = findById(id);
        user.setName(name);

        userRepository.save(user);
        return  UserMapper.INSTANCE.userToUserDTO(user);
    }

    @Override
    public UserDTO deleteById(String id) {
        User user = findById(id);
        userRepository.delete(user);
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @Override
    public UserDTO deleteCurrentUser(){
        User user = systemHelper.getCurrentUser();
        userRepository.delete(user);
        return UserMapper.INSTANCE.userToUserDTO(user);
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

    public User getUserByEmail(String email){
         return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(EntityConstant.NOT_FOUND_DATA));
    }

    private Role getRoleForUser(){
        return roleRepository.findByName(com.blog.mywebsite.enumerator.Role.USER.getValue())
                .orElseThrow(() -> new EntityNotFoundException(EntityConstant.NOT_FOUND_DATA));
    }
}