package com.mustycodified.ewalletAPIwithspringbootandMongoDB.controllers.restControllers;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.UpdatePasswordDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.UserSignupDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.ApiResponse;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.UserResponseDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.exceptions.AuthenticationException;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Tag(name = "User Endpoint", description = "Exposes REST API endpoints pertaining to users")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create a new user account",
            description = "After creating your account, an OTP will be sent to your provided email." +
            "\n Copy the code and navigate to 'activate-user' endpoint. \n")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201",
            description = "Successfully created a user")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@Valid @RequestBody UserSignupDto userDto){
       UserResponseDto userResponseDto = userService.signup(userDto);
       URI location = ServletUriComponentsBuilder.fromCurrentRequest()
               .path("/{uuid}")
               .buildAndExpand(userResponseDto.getUuid())
               .toUri();
       return ResponseEntity.created(location).body(new ApiResponse<>("Signup successful", true, userResponseDto));
    }

    @Operation(summary = "Updates a logged in user password, generates a new Bearer token and blacklists the old token")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Password updated successfully")
    @PostMapping("/update-password")
    public ResponseEntity<ApiResponse<UserResponseDto>> updatePassword(@RequestBody UpdatePasswordDto changePasswordDto, @RequestHeader("Authorization") String token){
        if (token.startsWith("Bearer")) {
            token = token.replace("Bearer", "").replaceAll("\\s", "");
        } else throw new AuthenticationException("Invalid access token. Please ensure token starts with 'Bearer '");
        changePasswordDto.setVerificationToken(token);
        return ResponseEntity.ok().body(new ApiResponse<>("Password updated successfully", true, userService.updatePassword(changePasswordDto)));
    }

    @Operation(summary = "Blacklist the users token")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Logout successful")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok().body(new ApiResponse<>("You have logged out", true, userService.logout(token)));

    }
}

