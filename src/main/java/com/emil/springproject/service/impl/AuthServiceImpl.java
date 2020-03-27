package com.emil.springproject.service.impl;

import com.emil.springproject.repository.AuthenticationRepository;
import com.emil.springproject.beans.Authentication;
import com.emil.springproject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Override
    @Transactional(readOnly = true)
    public Authentication saveAuthentication(Authentication authentication) {
        return authenticationRepository.save(authentication);
    }

    @Override
    @Transactional(readOnly = true)
    public Authentication findById(String id) {
        Optional<Authentication> authOptional = authenticationRepository.findById(id);
        return authOptional.orElse(null);
    }
}
