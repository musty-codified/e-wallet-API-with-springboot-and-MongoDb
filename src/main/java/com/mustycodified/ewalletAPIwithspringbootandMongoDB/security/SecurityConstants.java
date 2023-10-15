package com.mustycodified.ewalletAPIwithspringbootandMongoDB.security;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; //10days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String  HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL ="/api/v1/users/signup";
    public static final String UPDATE_PASSWORD_URL ="/api/v1/users/update-password";


}
