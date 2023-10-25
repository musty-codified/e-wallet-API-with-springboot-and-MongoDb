package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    @Schema(example = "nuban")
    private String type;

    private String account_name;

    @Schema(example = "Mustapha Musa")
    private String name;

    @Schema(example = "0001234567")
    private String account_number;

    @Schema(example = "057 for Zenith Bank")
    private String bank_code;

    @Schema(example = "NGN")
    private String currency;

}
