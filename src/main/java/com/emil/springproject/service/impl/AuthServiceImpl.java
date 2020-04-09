package com.emil.springproject.service.impl;

import com.emil.springproject.beans.User;
import com.emil.springproject.repository.AuthenticationRepository;
import com.emil.springproject.beans.Authentication;
import com.emil.springproject.repository.UserRepository;
import com.emil.springproject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Authentication saveAuthentication(Authentication authentication) {
        return authenticationRepository.save(authentication);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<org.springframework.security.core.userdetails.User> findByToken(String id) {
        Optional<Authentication> authOptional = authenticationRepository.findById(id);
        if (authOptional.isPresent()) {
            Authentication authentication = authOptional.get();
            Optional<User> userOptional = userRepository.findById(authentication.getUserId());
            //Add isPresent check
            User user = userOptional.get();
            org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), true, true,
                    true, true,
                    AuthorityUtils.createAuthorityList("USER"));
            return Optional.of(userDetails);
        }
        return Optional.empty();
    }
}
