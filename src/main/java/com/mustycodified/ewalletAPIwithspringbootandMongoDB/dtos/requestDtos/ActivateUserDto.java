package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "User activation details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivateUserDto {
    private String email;
    private String otp;
}
