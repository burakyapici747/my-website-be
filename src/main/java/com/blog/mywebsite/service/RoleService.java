package com.blog.mywebsite.service;


import com.blog.mywebsite.dto.RoleDTO;
import com.blog.mywebsite.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoleByUserEmail(String userEmail);
    RoleDTO create(com.blog.mywebsite.enumerator.Role roleName);
}
