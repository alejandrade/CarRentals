package com.techisgood.carrentals.security;

import com.techisgood.carrentals.authorities.UserAuthority;
import com.techisgood.carrentals.user.UserDemographicsDto;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.Optional;

public class SecurityUtils {
    public static void isAdminClerkOrSameUser(UserDemographicsDto requestBody, UserDetails userDetails) {
        Optional<? extends GrantedAuthority> any = userDetails.getAuthorities().stream()
                .filter(x -> UserAuthority.clerkOrAdmin(UserAuthority.valueOf(x.getAuthority()))).findAny();

        boolean clerkOrAdmin = any.isPresent();
        if (!clerkOrAdmin) {
            if (!Objects.equals(userDetails.getUsername(), requestBody.getUserId())) {
                throw new AccessDeniedException("you must be clerk, admin or same user to update demographics");
            }
        }
    }
}
