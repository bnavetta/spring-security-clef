package com.bennavetta.clef.sample;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>
{
    public User findByClefId(String clefId);
}
