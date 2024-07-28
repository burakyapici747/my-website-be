package com.blog.mywebsite.mapper;

import com.blog.mywebsite.api.output.UserOutput;
import com.blog.mywebsite.dto.UserDTO;
import com.blog.mywebsite.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "type", constant = "user")
    @Mapping(target = "attributes", source = ".")
    UserOutput userDTOToUserOutput(UserDTO userDTO);
    @Mapping(target = "type", constant = "user")
    @Mapping(target = "attributes", source = ".")
    List<UserOutput> userDTOListToUserOutputList(List<UserDTO> userDTOList);
    @Mapping(target = "id", ignore = true)
    UserOutput.Attributes toAttributes(UserDTO userDTO);
    UserDTO userToUserDTO(User user);
}