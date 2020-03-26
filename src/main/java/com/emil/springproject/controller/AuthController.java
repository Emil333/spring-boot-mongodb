package com.emil.springproject.controller;

import com.emil.springproject.beans.Authentication;
import com.emil.springproject.beans.User;
import com.emil.springproject.exception.ResourceNotFoundException;
import com.emil.springproject.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AuthenticationRepository authRepository;

    @GetMapping("/signin")
    public ResponseEntity userLogin(@RequestParam("email") String email, @RequestParam("password") String password)
            throws ResourceNotFoundException {

        Query query = new Query(Criteria.where("emailId").is(email).and("password").is(password));
        User user = mongoTemplate.findOne(query, User.class);
        if (user != null) {
            Authentication authentication = new Authentication();
            authentication.setUserId(user.getUserId());
            authRepository.save(authentication);
            return ResponseEntity.ok(authentication);
        } else {
            throw new ResourceNotFoundException("User with the provided credentials not found");
        }
    }
}
