package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {
    private String email;
    private String firstName;
    private String lastName;
    private String token;
}
