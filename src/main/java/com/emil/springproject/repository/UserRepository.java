package com.emil.springproject.repository;

import com.emil.springproject.beans.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
