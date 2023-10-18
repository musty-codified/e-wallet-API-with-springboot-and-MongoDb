package com.mustycodified.ewalletAPIwithspringbootandMongoDB.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("wallets")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@AllArgsConstructor
public class Wallet extends BaseEntity{

    @Indexed(unique = true)
    private String walletUUID;
    private String email;
    private BigDecimal balance;
}
