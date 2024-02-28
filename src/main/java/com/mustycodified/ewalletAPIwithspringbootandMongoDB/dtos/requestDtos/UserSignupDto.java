package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos;


import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(description = "User signup details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupDto {

    @NotBlank(message = "first name required")
    @Schema(description = "first name", example = "Mustapha")
    private String firstName;

    @NotBlank(message = "last name required")
    @Schema(description = "last name", example = "Musa")
    private String lastName;

    @NotBlank(message = "provide a valid email address")
    @Schema(description = "email address", example = "ilemonamustapha@gmail.com")
    private String email;

    @NotBlank
    @Schema(description = "country", example = "Nigeria")
    private String country;

    @NotBlank
    @Schema(description = "state", example = "Kogi")
    private String state;

    @NotBlank
    @Schema(description = "home address", example = "Ilara road, Ilisan Remo")
    private String homeAddress;

    @NotBlank(message = "password is required")
    @Schema(description = "password", example = "1994")
    private String password;

    @NotBlank
    @Schema(description = "mobileNumber", example = "08166099828")
    private String mobileNumber;
}
