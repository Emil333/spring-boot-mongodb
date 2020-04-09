package com.emil.springproject.service.impl;

import com.emil.springproject.beans.User;
import com.emil.springproject.repository.UserRepository;
import com.emil.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;


    @Override
    public Optional<User> findUserByEmail(String email) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnorePaths("userId", "password", "name");

        Example userRequest = Example.of(email, exampleMatcher);
        if (userRepository.exists(userRequest)) {
            return userRepository.findOne(userRequest);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> validateUserDetails(String email, String password) {
        User user = new User();
        user.setEmailId(email);
        user.setPassword(password);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("userId", "name");
        Example<User> userRequest = Example.of(user, exampleMatcher);
        return userRepository.findOne(userRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

}
