package com.emil.springproject.service;

import com.emil.springproject.beans.Authentication;

public interface AuthService {
    Authentication saveAuthentication(Authentication authentication);

    Authentication findById(String id);
}
