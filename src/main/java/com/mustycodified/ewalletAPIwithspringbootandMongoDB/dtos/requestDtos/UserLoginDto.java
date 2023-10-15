package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "User login request details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    private String email;
    private String password;
}
