package com.blog.mywebsite.service.impl;

import com.blog.mywebsite.api.response.DataResponse;
import com.blog.mywebsite.api.response.SuccessDataResponse;
import com.blog.mywebsite.constant.EntityConstant;
import com.blog.mywebsite.dto.RoleDTO;
import com.blog.mywebsite.mapper.RoleMapper;
import com.blog.mywebsite.model.Role;
import com.blog.mywebsite.repository.RoleRepository;
import com.blog.mywebsite.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserServiceImpl userService;
    public RoleServiceImpl(RoleRepository roleRepository, UserServiceImpl userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public List<Role> getRoleByUserEmail(String userEmail){
        return userService.getUserByEmail(userEmail).getRoles();
    }

    @Override
    public DataResponse<RoleDTO> create(com.blog.mywebsite.enumerator.Role roleName) {
        final Role role = new Role();
        role.setName(roleName.getValue());

        final RoleDTO roleDTO = RoleMapper.INSTANCE.roleToRoleDTO(roleRepository.save(role));

        return new SuccessDataResponse<>(roleDTO, EntityConstant.SUCCESS_CREATE);
    }
}