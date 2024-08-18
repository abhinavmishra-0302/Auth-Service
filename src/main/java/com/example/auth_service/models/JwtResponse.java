package com.example.auth_service.models;

public class JwtResponse {
    private String message;
    private String username;

    public JwtResponse() {}

    public JwtResponse(String message, String username) {
        this.message = message;
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
