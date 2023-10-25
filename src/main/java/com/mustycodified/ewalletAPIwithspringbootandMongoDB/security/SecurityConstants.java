package com.mustycodified.ewalletAPIwithspringbootandMongoDB.security;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; //10days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String  HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL ="/api/v1/users/signup";
    public static final String UPDATE_PASSWORD_URL ="/api/v1/users/update-password";
    public static final String LOG_TRANSACTION_URL ="/api/v1/payments/verify/{payment_reference}";
    public static final String TRANSFER_RECIPIENT_URL ="/api/v1/payments/withdrawal/create-transfer-recipient";
    public static final String CONFIRM_RECIPIENT_URL ="/api/v1/payments/validate-account-details";

}
