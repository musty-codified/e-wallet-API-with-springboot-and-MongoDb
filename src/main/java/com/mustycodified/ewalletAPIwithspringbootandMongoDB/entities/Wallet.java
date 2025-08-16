package com.mustycodified.ewalletAPIwithspringbootandMongoDB.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
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
