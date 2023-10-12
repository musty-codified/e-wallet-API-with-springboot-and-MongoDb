package com.mustycodified.ewalletAPIwithspringbootandMongoDB.controllers.restControllers;


import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.UserSignupDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.ApiResponse;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.UserResponseDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name="eWallet User RestController",
        description = "User Controller Exposes REST APIs endpoint for eWallet service"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Create and store new user REST API",
            description = "Creates and stores a user object in the database. After creating your account, a verification code will be sent to your provided email" +
            "\nCopy the code from you email and enter it in the 'activate-account end point'. \\n\""
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 created"
    )

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@RequestBody UserSignupDto userDto){
      return ResponseEntity.ok().body(new ApiResponse<>("User signup successful", true, userService.signup(userDto)));
    }
}
