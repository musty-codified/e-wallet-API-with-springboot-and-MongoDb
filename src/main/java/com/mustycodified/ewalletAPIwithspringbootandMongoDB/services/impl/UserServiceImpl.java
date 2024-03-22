package com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.impl;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.*;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.UserResponseDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.entities.User;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.entities.Wallet;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.enums.Roles;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.enums.Status;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.exceptions.AuthenticationException;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.exceptions.NotFoundException;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.exceptions.ValidationException;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.repositories.UserRepository;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.repositories.WalletRepository;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.security.CustomUserDetailService;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.security.JwtUtils;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.UserService;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.utils.AppUtils;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.utils.LocalMemStorage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
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
    private final MongoTemplate mongoTemplate;
    private final CustomUserDetailService customUserDetailService;
    private final JwtUtils jwtUtil;
    private final WalletRepository walletRepository;
    private final AuthenticationManager authenticationManager;
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserResponseDto signup(UserSignupDto userDto) {
        if (!appUtil.validEmail(userDto.getEmail())) {
            throw new ValidationException("Invalid email format {"+userDto.getEmail()+"}");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ValidationException("User with email: " + userDto.getEmail() + " already exists");
        }

        User newUser = appUtil.getMapper().convertValue(userDto, User.class);
        String userId = appUtil.generateSerialNumber("usr");
        newUser.setUuid(userId);
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setMobileNumber((appUtil.getFormattedNumber(userDto.getMobileNumber())));
        newUser.setRoles(Roles.ROLE_USER.getAuthorities().stream().map(Objects::toString).collect(Collectors.joining(",")));
        newUser.setStatus(Status.INACTIVE.name());

        newUser = userRepository.save(newUser);

        //self call
        sendToken(newUser.getEmail(), "activate your account");

        return appUtil.getMapper().convertValue(newUser, UserResponseDto.class);
    }

    @Override
    public UserResponseDto activateUser(ActivateUserDto activateUserDto) {
        System.out.println("Starting verification");
        appUtil.print(activateUserDto);

        validateOTP(activateUserDto.getEmail(), activateUserDto.getOtp());
        User userToActivate = userRepository.findByEmail(activateUserDto.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        userToActivate.setStatus(Status.ACTIVE.name());

        UserResponseDto userResponseDto = appUtil.getMapper().convertValue(userRepository.save(userToActivate), UserResponseDto.class);

        Wallet newWallet = Wallet.builder()
                .balance(BigDecimal.ZERO)
                .email(activateUserDto.getEmail())
                .walletUUID(appUtil.generateSerialNumber("zen"))
                .build();
        walletRepository.save(newWallet);

        MailDto mailDto = MailDto.builder()
                .to(activateUserDto.getEmail())
                .subject("YOUR ACCOUNT IS ACTIVATED")
                .body(String.format("Hi %s, \n You have successfully activated your account. Kindly login to start making use of the app", userResponseDto.getFirstName()))
                .build();

        emailService.sendMail(mailDto);

        return userResponseDto;
    }

    @Override
    public String sendToken(String userEmail, String mailSubject) {
        if (!userRepository.existsByEmail(userEmail))
            throw new ValidationException("User with email: " + userEmail + " does not exists");

            String otp = appUtil.generateSerialNumber("otp");
            memStorage.save(userEmail, otp, 900); //expires in 15mins

        MailDto mailDto = MailDto.builder()
                .to(userEmail)
                .subject(mailSubject.toUpperCase())
                .body(String.format("Use this OTP to %s; %s will expires in 15 minutes)", mailSubject.toLowerCase(), otp))
                .build();

        emailService.sendMail(mailDto);

        return "OTP sent";
    }

    @Override
    public UserResponseDto login(UserLoginDto creds) {
        if(!appUtil.validEmail(creds.getEmail()))
            throw new ValidationException("Invalid email");

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword())
            );
            UserResponseDto userResponseDto = null;

            if (authentication.isAuthenticated()) {
                User user = userRepository.findByEmail(creds.getEmail())
                        .orElseThrow(() -> new BadCredentialsException("Invalid login credentials"));

                if (user.getStatus().equals(Status.INACTIVE.name()))
                    throw new ValidationException("User not active. Please activate your account.");

                LOGGER.info("Generating access token for {}", user.getEmail());
                String accessToken = jwtUtil.generateToken(customUserDetailService.loadUserByUsername(user.getEmail()));
                Query query = new Query(Criteria.where("email").is(user.getEmail()));
                Update update = new Update().set("lastLoginDate", new Date());
                mongoTemplate.updateFirst(query, update, User.class);
                User loggedInUser = mongoTemplate.findOne(query, User.class);

                userResponseDto = appUtil.getMapper().convertValue(loggedInUser, UserResponseDto.class);
                userResponseDto.setToken(accessToken);
            } else {
                throw new BadCredentialsException("Invalid username or password");
            }
            return userResponseDto;

        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    @Override
    public String resetPassword(ChangePasswordDto changePasswordDto) {
        validateOTP(changePasswordDto.getEmail(), changePasswordDto.getOtp());
        User userToResetPassword = userRepository.findByEmail(changePasswordDto.getEmail())
                .orElseThrow(()-> new NotFoundException("User not found"));
        userToResetPassword.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));
        userRepository.save(userToResetPassword);

        //Send mail
        MailDto mailDto = MailDto.builder()
                .to(changePasswordDto.getEmail())
                .subject("PASSWORD RESET SUCCESSFUL")
                .body(String.format("Hi %s, your password has been reset successfully.", userToResetPassword.getFirstName()))
                .build();
        emailService.sendMail(mailDto);

        return "Password reset successful";
    }

    @Override
    public UserResponseDto updatePassword(UpdatePasswordDto updatePasswordDto) {
        if (jwtUtil.isTokenExpired(updatePasswordDto.getVerificationToken()))
            throw new ValidationException("Request token has already expired");

        blacklistToken(updatePasswordDto.getVerificationToken());

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String newToken = jwtUtil.generateToken(userDetails);

        User user = userRepository.findByEmail(updatePasswordDto.getEmail())
                .orElseThrow(() -> new AuthenticationException("User does not exist"));
        user.setPassword(passwordEncoder.encode(updatePasswordDto.getPassword()));
        User updatedUser = userRepository.save(user);
        appUtil.print(updatedUser);

        UserResponseDto userResponseDto = appUtil.getMapper().convertValue(updatedUser, UserResponseDto.class);
        userResponseDto.setToken(newToken);

        return userResponseDto;
    }

    @Override
    public String logout(String headerToken) {
        if (headerToken.startsWith("Bearer")) {
            headerToken = headerToken.replace("Bearer", "").replace("\\s", "");
        }
        blacklistToken(headerToken);
        return "Logout successful";
    }
    private void validateOTP(String memCachedKey, String value){
        if (!userRepository.existsByEmail(memCachedKey))
            throw new NotFoundException("User not found");
        if (!appUtil.validEmail(memCachedKey))
            throw new ValidationException("Invalid email");
        String cachedOTP = memStorage.getValueByKey(memCachedKey);
        if(cachedOTP == null)
            throw new ValidationException("OTP expired");
        if(!cachedOTP.equals(value))
            throw new ValidationException("Invalid OTP");

    }

    public void blacklistToken(String otp){
        Date expiryDate = jwtUtil.extractExpiration(otp);
        int expiryTimeInSeconds = (int) ((expiryDate.getTime() - new Date().getTime())/1000);
        memStorage.setBlacklist(otp, expiryTimeInSeconds);
    }
}
