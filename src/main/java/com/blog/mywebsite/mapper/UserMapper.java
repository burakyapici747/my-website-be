package com.blog.mywebsite.mapper;

import com.blog.mywebsite.dto.UserDTO;
import com.blog.mywebsite.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);
}
