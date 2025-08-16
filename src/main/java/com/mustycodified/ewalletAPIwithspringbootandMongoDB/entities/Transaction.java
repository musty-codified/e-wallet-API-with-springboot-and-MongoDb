package com.mustycodified.ewalletAPIwithspringbootandMongoDB.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Document("transactions")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends BaseEntity{

    @Indexed(unique = true)
    private String reference;

    private String transactionType;

    private String transactionStatus;

    private String email;

    private BigDecimal amount;

    private String currency;

    private String status;

    private String domain;

    private String gateway_response;

    private String message;

    private String channel;

    private String ip_address;

}
