package com.bennavetta.clef.example.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>
{
    User findByClefId(String clefId);
}
