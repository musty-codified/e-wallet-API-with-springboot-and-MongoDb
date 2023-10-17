package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitiateTransactionDto {
    @NotBlank(message = "email must be provided")
    private String email;

    @NotBlank(message = "amount must be provided and must be specified in subunit of kobo")
    private String amount;

    private String callback_url;

    private Object metadata;
}
