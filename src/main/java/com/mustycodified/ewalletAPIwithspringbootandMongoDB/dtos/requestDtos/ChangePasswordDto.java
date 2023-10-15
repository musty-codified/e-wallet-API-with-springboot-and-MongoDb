package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {
    @NotBlank(message = "email must be provided")
    private String email;
    @NotBlank(message = "password must be provided")
    private String password;
    private String otp;
}
