package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "User activation details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivateUserDto {

    private String email;
    private Long verificationOTP;
}
