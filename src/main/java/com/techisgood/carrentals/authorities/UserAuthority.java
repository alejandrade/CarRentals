package com.techisgood.carrentals.authorities;

public enum UserAuthority {
    ROLE_ANON,
    ROLE_USER,
    ROLE_CLERK,
    ROLE_ADMIN,
    ROLE_STAFF;


    public static Boolean clerkOrAdmin(UserAuthority userAuthority) {
        return userAuthority.equals(ROLE_ADMIN) || userAuthority.equals(ROLE_CLERK);
    }
}
