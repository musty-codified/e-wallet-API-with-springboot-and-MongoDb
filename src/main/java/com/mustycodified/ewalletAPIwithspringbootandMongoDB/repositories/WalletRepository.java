package com.mustycodified.ewalletAPIwithspringbootandMongoDB.repositories;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.entities.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WalletRepository extends MongoRepository<Wallet, String> {
    Optional<Wallet> findByEmail(String email);
}
