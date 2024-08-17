package com.example.auth_service.services;

import com.example.auth_service.models.RolesInfo;
import com.example.auth_service.models.UserInfo;
import com.example.auth_service.repositories.RoleRepository;
import com.example.auth_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void registerUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        Set<RolesInfo> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
        userInfo.setRoles(roles);
        userRepository.save(userInfo);
    }

    public Optional<UserInfo> findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
