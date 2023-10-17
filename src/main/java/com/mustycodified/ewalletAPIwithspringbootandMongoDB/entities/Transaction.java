package com.mustycodified.ewalletAPIwithspringbootandMongoDB.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("transactions")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
public class Transaction {

    @Indexed(unique = true)
    private String reference;

    private String transactionType;

    private String transactionStatus;

    private String email;

    private BigDecimal amount;

    private String currency;

}
