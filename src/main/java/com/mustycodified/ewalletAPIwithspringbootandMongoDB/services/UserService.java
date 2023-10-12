package com.mustycodified.ewalletAPIwithspringbootandMongoDB.services;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.ActivateUserDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.UserSignupDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.UserResponseDto;

public interface UserService {
    UserResponseDto signup(UserSignupDto userDto);
    UserResponseDto activateUser(ActivateUserDto activateUserDto);
    String sendToken(String userEmail, String mailSubject);


}
