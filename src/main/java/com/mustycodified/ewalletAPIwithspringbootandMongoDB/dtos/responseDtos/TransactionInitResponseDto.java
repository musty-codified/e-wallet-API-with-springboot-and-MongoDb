package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private String authorization_url;
    private String access_code;
    private String reference;
    private BigDecimal amount;
    private String status;
    private String domain;
    private String gateway_response;
    private String message;
    private String channel;

}
