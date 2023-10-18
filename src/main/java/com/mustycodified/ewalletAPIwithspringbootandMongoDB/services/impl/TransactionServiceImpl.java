package com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.impl;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.InitiateTransactionDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.ApiResponse;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.TransactionInitResponseDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.entities.Transaction;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.enums.TransactionType;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.repositories.TransactionRepository;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.TransactionService;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.WalletService;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final RestTemplate restTemplate;
    private final AppUtils appUtil;

    private final TransactionRepository transactionRepository;

    private final WalletService walletService;
    @Value("${api.paystack_secret}")
    private String apiKey;

    private Map<String, Transaction> transactionsMap = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    @Override
    public ApiResponse initiateTransaction(InitiateTransactionDto transactionDto) {
        String url = "https://api.paystack.co/transaction/initialize";
       transactionDto.setAmount(transactionDto.getAmount() + "00");

        //Set authorization header for querying Paystack's end points
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + apiKey);
        httpHeaders.add("Content-Type", "application/json");

        HttpEntity <InitiateTransactionDto> entity = new HttpEntity<>(transactionDto, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.POST, entity, ApiResponse.class).getBody();

    }

    @Override
    public ApiResponse<TransactionInitResponseDto> verifyTransaction(String reference) {
        String url = "https://api.paystack.co/transaction/verify/ " + reference;

        //Set authorization header for querying Paystack's end points
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + apiKey);
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity entity = new HttpEntity(httpHeaders);

     ResponseEntity<ApiResponse> responseDto =
              restTemplate.exchange(url, HttpMethod.GET, entity, ApiResponse.class);

     TransactionInitResponseDto responseData =
             appUtil.getMapper().convertValue(Objects.requireNonNull(responseDto.getBody().getData()), TransactionInitResponseDto.class);

      responseData.setAmount(responseData.getAmount().divide(new BigDecimal(100), 2, RoundingMode.DOWN));
         appUtil.print("Initiate transaction response data " + responseData);

         //Log transaction if transaction is not logged...
        if (!transactionRepository
                .existsByReferenceOrId(responseData.getReference(), responseData.getId())) {

            if (responseData.getStatus().equalsIgnoreCase("success")) { // Update wallet only when transaction is fulfilled
                walletService.updateWallet(responseData.getCustomer().getEmail(), responseData.getAmount());
            }

            //save transaction to DB for transaction history purposes
            responseData.setTransactionType(TransactionType.TRANSACTION_TYPE_DEPOSIT.getTransaction());
            this.saveTransaction(responseData);
        }

        return new ApiResponse<>(responseData.getStatus(),
                responseData.getGateway_response().equalsIgnoreCase("success"), responseData);
    }

    @Override
    public TransactionInitResponseDto saveTransaction(TransactionInitResponseDto transactionDto) {

        if (!transactionRepository
                .existsByReferenceOrId(transactionDto.getReference(), transactionDto.getId())) {

            Transaction transaction = appUtil.getMapper().convertValue(transactionDto, Transaction.class);
            transaction.setEmail(transactionDto.getCustomer().getEmail());

            try{
                transactionRepository.save(transaction);

            } catch (Exception e){
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }


        return transactionDto;
    }
}
