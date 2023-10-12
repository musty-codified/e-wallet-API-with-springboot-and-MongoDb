package com.mustycodified.ewalletAPIwithspringbootandMongoDB.enums;

public enum Authorities {
    READ_AUTHORITY("read:authority"),
    WRITE_AUTHORITY("write:authority"),
    DELETE_AUTHORITY("delete:authority");

    private final String authorities;

    Authorities(String authorities){
        this.authorities = authorities;
    }
    public String getAuthorities() {
        return authorities;
    }
}
