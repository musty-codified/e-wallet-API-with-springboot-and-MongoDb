package com.mustycodified.ewalletAPIwithspringbootandMongoDB.services;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.AccountDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.InitiateTransactionDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.ApiResponse;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.BankDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.FundTransferDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.TransactionInitResponseDto;

import java.util.List;

public interface TransactionService {

    ApiResponse<String> initiateTransaction(InitiateTransactionDto transactionDto);
    ApiResponse <TransactionInitResponseDto> verifyTransaction(String reference);
    ApiResponse<List<BankDto>> fetchBanks(String currency, String type);

    ApiResponse<FundTransferDto> createTransferRecipient(AccountDto accountDto);

    TransactionInitResponseDto saveTransaction(TransactionInitResponseDto transactionDto);

   ApiResponse<AccountDto> resolveBankDetails(String accountNumber, String bankCode);

   ApiResponse<TransactionInitResponseDto> initiateTransfer( FundTransferDto fundTransferDto);
}
