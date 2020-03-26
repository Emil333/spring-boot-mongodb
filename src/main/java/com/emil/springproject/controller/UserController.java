package com.emil.springproject.controller;

import com.emil.springproject.beans.User;
import com.emil.springproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/v1/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity addUser(@Valid @RequestBody User user) {

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnorePaths("userId", "password", "name");

        Example userRequest = Example.of(user, exampleMatcher);
        if (userRepository.exists(userRequest)) {
            return ResponseEntity.status(403).body("User with the email already exists");
        } else {
            return ResponseEntity.ok(userRepository.save(user));
        }
    }
}
