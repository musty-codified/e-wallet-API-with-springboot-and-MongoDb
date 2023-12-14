package com.mustycodified.ewalletAPIwithspringbootandMongoDB.enums;

import com.google.common.collect.Sets;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mustycodified.ewalletAPIwithspringbootandMongoDB.enums.Authorities.*;

public enum Roles {

    ROLE_USER(Sets.newHashSet(USER_READ, USER_EDIT)),
    ROLE_ADMIN(Sets.newHashSet(USER_READ, USER_EDIT, ACCOUNT_EDIT, ACCOUNT_READ));

    private final Set<Authorities> authorities;

    Roles(Set<Authorities> authorities){
        this.authorities = authorities;

    }
    public Set<Authorities> getAuthorities(){
        return this.authorities;
    }

    class Testing {
        public static void main(String[] args) {
            System.out.println(Roles.ROLE_USER.getAuthorities().stream().map(Objects::toString).collect(Collectors.joining(",")));
        }
}
}