package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos;


import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(description = "User details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupDto {

    @NotNull(message = "first name required")
    @Schema(description = "first name", example = "Mustapha")
    private String firstName;

    @NotNull
    @Schema(description = "last name", example = "Musa")
    private String lastName;

    @NotNull
    @Schema(description = "email address", example = "ilemonamustapha@gmail.com")
    private String email;

    @NotNull
    @Schema(description = "country", example = "Nigeria")
    private String country;

    @NotNull
    @Schema(description = "state", example = "Kogi")
    private String state;

    @NotNull
    @Schema(description = "home address", example = "Ilara road, Ilisan")
    private String homeAddress;

    @NotNull
    @Schema(description = "passsword", example = "1994")
    private String password;

    @NotNull
    @Schema(description = "phoneNumber", example = "08166099828")
    private String phoneNumber;
}
