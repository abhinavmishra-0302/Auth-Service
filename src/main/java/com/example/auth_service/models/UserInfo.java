package com.example.auth_service.models;


import jakarta.persistence.*;

import java.util.Set;

@Entity
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<RolesInfo> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RolesInfo> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolesInfo> roles) {
        this.roles = roles;
    }
}
