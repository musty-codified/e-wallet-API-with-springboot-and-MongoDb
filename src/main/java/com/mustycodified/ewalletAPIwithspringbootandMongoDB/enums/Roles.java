package com.mustycodified.ewalletAPIwithspringbootandMongoDB.enums;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.mustycodified.ewalletAPIwithspringbootandMongoDB.enums.Authorities.*;

public enum Roles {
    ROLE_USER(Sets.newHashSet(READ_AUTHORITY)),
    ROLE_ADMIN(Sets.newHashSet(READ_AUTHORITY, WRITE_AUTHORITY)),
    ROLE_SUPER_ADMIN(Sets.newHashSet(READ_AUTHORITY, WRITE_AUTHORITY, DELETE_AUTHORITY));

    private final Set<Authorities> authorities;

    Roles(Set<Authorities> authorities){
        this.authorities = authorities;

    }
    public Set<Authorities> getAuthorities(){
        return this.authorities;
    }
}
