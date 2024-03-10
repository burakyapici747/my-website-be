package com.blog.mywebsite.service.impl;

import com.blog.mywebsite.api.request.UserCreateRequest;
import com.blog.mywebsite.api.response.*;
import com.blog.mywebsite.constant.EntityConstant;
import com.blog.mywebsite.dto.UserDTO;
import com.blog.mywebsite.enumerator.Role;
import com.blog.mywebsite.exception.EntityExistException;
import com.blog.mywebsite.model.User;
import com.blog.mywebsite.repository.UserRepository;
import com.blog.mywebsite.service.UserService;
import com.blog.mywebsite.util.security.JWTHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public DataResponse<UserDTO> getById(String id) {
        return null;
    }

    //TODO Kullanıcının oluşturmak istediği mail sistemde kayıtlı değil ise, token oluşturup
    //TODO Mail adresine link gönderilecek ve mail adresine gelen link üzerinden sisteme giriş yapıp kayıt olmuş olacak.
    @Override
    public DataResponse<String> create(UserCreateRequest userCreateRequest) {
        checkUserIsExistByEmail(userCreateRequest.email());

        String jwtToken = JWTHelper.generateJwtToken(userCreateRequest.email(), List.of(Role.USER.getValue()));

//        final User user = new User();
//
//        user.setName(userCreateRequest.name());
//        user.setEmail(userCreateRequest.email());
//        user.setRole(Role.USER);
//
//
//
//        final UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(userRepository.save(user));

        //return new SuccessDataResponse<>(userDTO, EntityConstant.SUCCESS_CREATE);

        return new SuccessDataResponse<>(jwtToken, "Token is success");
    }

    @Override
    public BaseResponse deleteById(String id) {
        return null;
    }

    @Override
    public DataResponse<UserDTO> updateNameById(String id, String name) {
        return null;
    }

    private void checkUserIsExistByEmail(String email){
        if(userRepository.existsByEmail(email)){
            throw new EntityExistException("This email address (" + email + ") is already in use.");
        }
    }

    private User getUserByEmail(String email){
         return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(EntityConstant.NOT_FOUND_DATA));
    }
}