package com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.impl;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.ActivateUserDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.MailDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.UserSignupDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.UserResponseDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.entities.User;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.enums.Roles;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.enums.Status;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.exceptions.NotFoundException;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.exceptions.ValidationException;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.repositories.UserRepository;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.UserService;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.utils.AppUtils;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.utils.LocalMemStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final AppUtils appUtil;
    private final UserRepository userRepository;
    private final LocalMemStorage memStorage;
    private final EmailServiceImpl emailService;
    @Override
    public UserResponseDto signup(UserSignupDto userDto) {

        //Validate registrant
        if (!appUtil.validEmail(userDto.getEmail())) {
            throw new ValidationException("Invalid email format {"+userDto.getEmail()+"}");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ValidationException("User with email: " + userDto.getEmail() + " already exists");
        }

        //Remember Registrant
        User newUser = appUtil.getMapper().convertValue(userDto, User.class);
        String userId = appUtil.generateSerialNumber("usr");
        newUser.setUuid(userId);
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRoles(Roles.ROLE_USER.getAuthorities().stream().map(Objects::toString).collect(Collectors.joining(",")));
        newUser.setStatus(Status.INACTIVE.name());

        newUser = userRepository.save(newUser);

        sendToken(newUser.getEmail(), "Activate your account");

        return appUtil.getMapper().convertValue(newUser, UserResponseDto.class);    }

    @Override
    public UserResponseDto activateUser(ActivateUserDto activateUserDto) {
     validateOTP(activateUserDto.getEmail(), activateUserDto.getVerificationOTP());
        User userToActivate = userRepository.findByEmail(activateUserDto.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        userToActivate.setStatus(Status.ACTIVE.name());
       User activatedUser = userRepository.save(userToActivate);
        UserResponseDto userResponseDto = appUtil.getMapper().convertValue(activatedUser, UserResponseDto.class);

        return userResponseDto;
    }

    @Override
    public String sendToken(String userEmail, String mailSubject) {
        //Validate email existence
        if (!userRepository.existsByEmail(userEmail))
            throw new ValidationException("User with email: " + userEmail + " does not exists");

            Long otp = appUtil.generateOTP();
            memStorage.save(userEmail, otp, 900); //expires in 15mins

        MailDto mailDto = MailDto.builder()
                .to(userEmail)
                .subject(mailSubject.toUpperCase())
                .body(String.format("Use this generated OTP to %s: %s (Expires in 15mins)", mailSubject.toLowerCase(), otp))
                .build();

        emailService.sendMail(mailDto);

        return "OTP sent";
    }

    public void validateOTP(String memCachedKey, Long value){
        if (!appUtil.validEmail(memCachedKey))
            throw new ValidationException("Invalid email");

        Long cachedOTP = memStorage.getValueByKey(memCachedKey);
        if(cachedOTP == null)
            throw new ValidationException("OTP expired");
        if(!cachedOTP.equals(value))
            throw new ValidationException("Invalid OTP");
        if (!userRepository.existsByEmail(memCachedKey))
            throw new NotFoundException("User not found");


    }
}
