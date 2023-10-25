package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundTransferDto {

    @Schema(example = "RCP_1a25w1h3n0xctjg")
    private String recipient_code;

    @Schema(example = "TRF_1a25w1h3n0xctjg")
    private String reference;

    @NotBlank
    @Schema(example = "ilemonamustapha@gmail.com")
    private String email;

    @Schema(example = "30000")
    private BigDecimal amount;

    @Schema(example = "balance")
    private String source;

    @Schema(example = "For party")
    private String reason;
}
