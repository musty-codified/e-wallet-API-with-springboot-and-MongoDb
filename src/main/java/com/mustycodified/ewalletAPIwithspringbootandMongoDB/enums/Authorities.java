package com.mustycodified.ewalletAPIwithspringbootandMongoDB.enums;

public enum Authorities {
    USER_READ("user:read"),
    USER_EDIT("user:write"),
    ACCOUNT_READ("account:read"),
    ACCOUNT_EDIT("account:edit"),
    ACCOUNT_WITHDRAW("account:withdraw"),
    ACCOUNT_DEPOSIT("account:deposit");
    private final String authorities;

    Authorities(String authorities){
        this.authorities = authorities;
    }
    public String getAuthorities() {
        return authorities;
    }
}
