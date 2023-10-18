package com.mustycodified.ewalletAPIwithspringbootandMongoDB.repositories;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.entities.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    boolean existsByReferenceOrId(String reference, String id);
}
