package com.emil.springproject.controller;

import com.emil.springproject.beans.Authentication;
import com.emil.springproject.beans.User;
import com.emil.springproject.exception.ResourceNotFoundException;
import com.emil.springproject.service.AuthService;
import com.emil.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @GetMapping("/signin")
    public ResponseEntity userLogin(@RequestParam("email") String email, @RequestParam("password") String password)
            throws ResourceNotFoundException {

        Optional<User> userOptional = userService.validateUserDetails(email, password);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Authentication authentication = new Authentication();
            authentication.setUserId(user.getUserId());
            return ResponseEntity.ok(authService.saveAuthentication(authentication));
        } else {
            throw new ResourceNotFoundException("User with the provided credentials not found");
        }
    }
}
