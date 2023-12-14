package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionListDto {
    private String message;
    private boolean status;
    private List<TransactionInitResponseDto> data;
}
