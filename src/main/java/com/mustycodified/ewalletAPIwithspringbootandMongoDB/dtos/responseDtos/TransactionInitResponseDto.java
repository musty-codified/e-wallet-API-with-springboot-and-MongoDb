package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionInitResponseDto {

    private String id;
    private String reference;
    private BigDecimal amount;
    //!
    private String status;
    private String domain;
    private Customer customer;
    //!
    private String gateway_response;
    private String message;
    private String channel;
    private String currency;
    private String transactionType;
    private String ip_address;

}
