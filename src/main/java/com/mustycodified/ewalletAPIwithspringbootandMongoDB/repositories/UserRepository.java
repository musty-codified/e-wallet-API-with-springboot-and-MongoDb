package com.mustycodified.ewalletAPIwithspringbootandMongoDB.repositories;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
