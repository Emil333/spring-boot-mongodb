package com.emil.springproject.service;

import com.emil.springproject.beans.Authentication;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface AuthService {
    Authentication saveAuthentication(Authentication authentication);

    Optional<User> findByToken(String token);
}
