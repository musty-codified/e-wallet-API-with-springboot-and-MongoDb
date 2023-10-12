package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private String uuid;
    private String email;
    private String firstName;
    private String lastName;
    private String homeAddress;
    private String phoneNumber;
    private String state;
    private String token;
}
