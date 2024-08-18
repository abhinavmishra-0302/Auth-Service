package com.example.auth_service.controller;

import com.example.auth_service.models.JwtResponse;
import com.example.auth_service.models.UserInfo;
import com.example.auth_service.services.TokenBlackListService;
import com.example.auth_service.services.UserService;
import com.example.auth_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

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

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestParam String token){
        if (jwtUtil.validateToken(token)) {
            System.out.println(jwtUtil.getUsernameFromToken(token)) ;
            String username = jwtUtil.getUsernameFromToken(token);
            return ResponseEntity.ok(new JwtResponse("Token is valid", username));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
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
