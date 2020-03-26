package com.emil.springproject.repository;

import com.emil.springproject.beans.Authentication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends MongoRepository<Authentication, String> {


}
