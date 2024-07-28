package com.mustycodified.ewalletAPIwithspringbootandMongoDB.controllers.auth;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.ActivateUserDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.ChangePasswordDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.UserLoginDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.ApiResponse;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.UserResponseDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @Operation(summary = "Resend account activation token or password reset token", description = "Verification code will be sent to your provided email")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OTP resent successfully")
    @PostMapping("/resend-token")
    public ResponseEntity<ApiResponse<String>> resendToken(@RequestParam String userEmail, @RequestParam String mailSubject){
      return ResponseEntity.ok(new ApiResponse<>("token resent", true, userService.sendToken(userEmail, mailSubject)));
    }
    @Operation(summary = "Activates a newly created account or an inactive account after token confirmation", description = "Once activated, navigate to login route.\n" + "To proceed, ensure that you enter the complete OTP sent to your email.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Account activated successfully")
    @PostMapping("/activate-user")
    public ResponseEntity<ApiResponse<UserResponseDto>> activateUser(@RequestBody ActivateUserDto activateUserDto){
        return ResponseEntity.ok( new ApiResponse<>("User activated!", true, userService.activateUser(activateUserDto)));
    }
    @Operation(summary = "Generates a JWT token upon successful login that will be used for Authorization",
            description = "You will need to append the string 'Bearer ' to the token before adding it to your Authorization header. NOTE: " + "there's a single space after the 'Bearer ' string. Don't forget to include it.")
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200", description = "Successful login. Returns a JWT token in the response body",
                            content = @Content(schema = @Schema(implementation = UserResponseDto.class), mediaType = "application/json"),
                            headers = {@Header(name = "authorization", description = "Bearer <JWT value here>", schema = @Schema(type = "string"))}
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDto>> loginUser(@RequestBody UserLoginDto userLoginDto){
        return ResponseEntity.ok( new ApiResponse<>("Login successful!", true, userService.login(userLoginDto)));
    }

    @Operation(summary = "Resets a password after a reset verification OTP has been confirmed",
            description = "Before using this endpoint, ensure the verification OTP has been activated")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Password reset successful")

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody ChangePasswordDto changePasswordDto){
        return ResponseEntity.ok( new ApiResponse<>("Password Reset successful!", true, userService.resetPassword(changePasswordDto)));
    }
}
