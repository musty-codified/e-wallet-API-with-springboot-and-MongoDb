package com.mustycodified.ewalletAPIwithspringbootandMongoDB.services;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.*;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.UserResponseDto;

public interface UserService {
    UserResponseDto signup(UserSignupDto userDto);
    UserResponseDto activateUser(ActivateUserDto activateUserDto);
    String sendToken(String userEmail, String mailSubject);
    UserResponseDto login(UserLoginDto userLoginDto);
    String resetPassword(ChangePasswordDto changePasswordDto);
    UserResponseDto updatePassword(UpdatePasswordDto updatePasswordDto);
    String logout(String token);


}
