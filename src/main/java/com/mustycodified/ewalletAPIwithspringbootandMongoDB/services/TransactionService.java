package com.mustycodified.ewalletAPIwithspringbootandMongoDB.services;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.InitiateTransactionDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.ApiResponse;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.TransactionInitResponseDto;

public interface TransactionService {

    ApiResponse initiateTransaction(InitiateTransactionDto transactionDto);
    ApiResponse <TransactionInitResponseDto >verifyTransaction(String reference);

    TransactionInitResponseDto saveTransaction(TransactionInitResponseDto transactionDto);
}
