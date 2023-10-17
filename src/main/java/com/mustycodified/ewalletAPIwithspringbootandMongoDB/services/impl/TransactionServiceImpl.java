package com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.impl;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.InitiateTransactionDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.ApiResponse;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final RestTemplate restTemplate;
    @Value("${api.paystack_secret}")
    private String apiKey;

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
}
