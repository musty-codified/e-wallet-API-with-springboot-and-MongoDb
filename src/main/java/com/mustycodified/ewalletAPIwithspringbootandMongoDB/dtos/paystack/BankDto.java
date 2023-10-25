package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class BankDto {
    private String name;
    private String code;
    private boolean active;
    private String is_deleted;
    private String country;
    private String currency;
    private String type;
    private String id;
}
