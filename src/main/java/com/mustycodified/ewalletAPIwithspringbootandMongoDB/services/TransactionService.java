package com.mustycodified.ewalletAPIwithspringbootandMongoDB.services;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.AccountDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.InitiateTransactionDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.ApiResponse;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.BankDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.FundTransferDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.TransactionInitResponseDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.TransferRecipientDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {

    ApiResponse<String> initiateTransaction(InitiateTransactionDto transactionDto);
    ApiResponse <TransactionInitResponseDto> verifyTransaction(String reference);
    Page<TransactionInitResponseDto> listTransactions(int perPage, int page, String sortBy, String sortDir);

    ApiResponse<List<BankDto>> fetchBanks(String currency, String type);

    ApiResponse<List<TransactionInitResponseDto>> listPaystackTrans(int perPage, int page);

    ApiResponse<FundTransferDto> createTransferRecipient(AccountDto accountDto);

    Page<TransferRecipientDto> listTransferRecipient(int perPage, int page);

    TransactionInitResponseDto saveTransaction(TransactionInitResponseDto transactionDto);

   ApiResponse<AccountDto> resolveBankDetails(String accountNumber, String bankCode);

   ApiResponse<TransactionInitResponseDto> initiateTransfer( FundTransferDto fundTransferDto);


}
