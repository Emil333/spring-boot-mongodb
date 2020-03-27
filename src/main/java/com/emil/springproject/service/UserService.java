package com.emil.springproject.service;

import com.emil.springproject.beans.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findUserByEmail(String email);
    User saveUser(User user);
    Optional<User> validateUserDetails(String email, String password);
    Optional<User> findUserById(String id);
}
