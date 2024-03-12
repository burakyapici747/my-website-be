package com.blog.mywebsite.model;

import com.blog.mywebsite.enumerator.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "USER")
public class User extends BaseEntity {
    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}