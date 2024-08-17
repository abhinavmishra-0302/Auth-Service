package com.example.auth_service.controller;

import com.example.auth_service.models.UserInfo;
import com.example.auth_service.services.TokenBlackListService;
import com.example.auth_service.services.UserService;
import com.example.auth_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class    AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenBlackListService tokenBlackListService;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserInfo userInfo){
        userService.registerUser(userInfo);
        return "User registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserInfo userInfo){
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userInfo.getUsername(), userInfo.getPassword()));
            UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
            System.out.println(authenticate.getPrincipal());
            return jwtUtil.generateToken(userDetails);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "Invalid username or password";
        }
    }

    @PostMapping("/logout")
    public String logout(@RequestBody String token){
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            tokenBlackListService.blackListToken(token);
            return "User logged out successfully";
        }
        return "Invalid token";
    }
}
