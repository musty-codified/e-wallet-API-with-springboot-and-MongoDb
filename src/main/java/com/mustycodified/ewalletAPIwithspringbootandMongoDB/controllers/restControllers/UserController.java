package com.mustycodified.ewalletAPIwithspringbootandMongoDB.controllers.restControllers;


import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.ChangePasswordDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.UpdatePasswordDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.UserSignupDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.ApiResponse;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.UserResponseDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.exceptions.AuthenticationException;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "eWallet-application", description = "Exposes REST API endpoints pertaining to users")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create and store new user Restful web service endpoint",
            description = "Creates and stores a user object in the database. After creating your account, an OTP will be sent to your provided email" +
            "\n.Copy the code from you email and enter it in the 'activate-user endpoint'. \n")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Successfully created a user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@RequestBody UserSignupDto userDto){
      return ResponseEntity.ok().body(new ApiResponse<>("User signup successful", true, userService.signup(userDto)));
    }

    @Operation(summary = "Updates a logged in user password, generates a new Bearer token and blacklists the old token")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Password updated successfully")
    @PostMapping("/update-password")
    public ResponseEntity<ApiResponse<UserResponseDto>> updatePassword(@RequestBody UpdatePasswordDto changePasswordDto, @RequestHeader("Authorization") String token){
        if (token.startsWith("Bearer")) {
            token = token.replace("Bearer", "").replaceAll("\\s", "");
        } else throw new AuthenticationException("Invalid access token. Pls ensure token starts with 'Bearer '");
        changePasswordDto.setVerificationToken(token);
        return ResponseEntity.ok().body(new ApiResponse<>("Password updated successfully", true, userService.updatePassword(changePasswordDto)));
    }

    @Operation(summary = "Log out of the system by blacklisting the users token")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Logout successful")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok().body(new ApiResponse<>("You are currently logged out!", true, userService.logout(token)));

    }
}

