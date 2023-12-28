package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "User login request details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    @Schema(description = "email", example = "ilemonamustapha@gmail.com")
    private String email;

    @Schema(description = "password", example = "1994")
    private String password;
}
