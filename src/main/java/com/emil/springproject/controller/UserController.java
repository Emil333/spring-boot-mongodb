package com.emil.springproject.controller;

import com.emil.springproject.service.UserService;
import com.emil.springproject.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/v1/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity addUser(@Valid @RequestBody User user) {
        Optional<User> userOptional = userService.findUserByEmail(user.getEmailId());
        if (userOptional.isPresent()) {
            return ResponseEntity.status(403).body("User with the email already exists");
        } else {
            return ResponseEntity.ok(userService.saveUser(user));
        }
    }
}
