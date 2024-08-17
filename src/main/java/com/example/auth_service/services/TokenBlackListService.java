package com.example.auth_service.services;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlackListService {
    private final Set<String> blackList = new HashSet<>();

    public void blackListToken(String token){
        blackList.add(token);
    }

    public boolean isTokenBlackListed(String token){
        return blackList.contains(token);
    }
}
