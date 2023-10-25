package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.AccountDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class TransferRecipientDto {
    private boolean active;
    private String email;
    private String name;
    private String currency;
    private String recipient_code;
    private String domain;
    private String integration;
    private String type;
    private Date createdAt;
    private Date updatedAt;
    private boolean is_deleted;
    private AccountDto details;
}
