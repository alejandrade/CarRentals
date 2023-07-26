package com.techisgood.carrentals.security;

import com.techisgood.carrentals.model.DbUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {

    private final String id;
    private final String email;
    private final String phoneNumber;
    private final Boolean isEmailAuth;
    private final Boolean enabled;
    private final Boolean accountNonExpired;
    private final Boolean accountNonLocked;
    private final Boolean credentialsNonExpired;
    private final Collection<? extends GrantedAuthority> authorities;


    public static UserPrincipal create(DbUser dbUser) {
    	List<SimpleGrantedAuthority> grantedAuthorities = dbUser.getAuthorities().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
                .collect(Collectors.toList());
    	
        return new UserPrincipal(
                dbUser.getId(),
                dbUser.getEmail(),
                dbUser.getPhoneNumber(),
                dbUser.getIsEmailAuth(),
                dbUser.getEnabled(),
                dbUser.getAccountNonExpired(),
                dbUser.getAccountNonLocked(),
                dbUser.getCredentialsNonExpired(),
                grantedAuthorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null; // Since we're using JWT, we don't need to store the password in UserPrincipal
    }

    @Override
    public String getUsername() {
        return isEmailAuth ? email : phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    // You can also add getters for other attributes like id, phoneNumber, etc. if needed.
}
