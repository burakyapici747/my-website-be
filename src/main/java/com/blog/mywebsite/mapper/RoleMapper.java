package com.blog.mywebsite.mapper;

import com.blog.mywebsite.dto.RoleDTO;
import com.blog.mywebsite.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    RoleDTO roleToRoleDTO(Role role);
}
