package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Schema(description = "Response sent back to the user")

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    @Schema(description = "unique user ID")
    private String uuid;

    @Schema(description = "User email")
    private String email;

    @Schema(description = "first name")
    private String firstName;

    @Schema(description = "last name")
    private String lastName;

    @Schema(description = "home address")
    private String homeAddress;

    @Schema(description = "phone number")
    private String phoneNumber;

    @Schema(description = "state of origin")
    private String state;

    @Schema(description = "authorization token")
    private String token;
}
