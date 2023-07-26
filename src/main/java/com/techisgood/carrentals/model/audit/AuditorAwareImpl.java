package com.techisgood.carrentals.model.audit;

import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.user.UserByEmailOrPhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    private final UserByEmailOrPhoneService userContextService;

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        // If using Spring Security's default UserDetails implementation
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<DbUser> loggedInUser = userContextService.getUser(username);
            return loggedInUser.map(DbUser::getId);
        } else {
            // If the principal object isn't of UserDetails type, it's typically a String with the username:
            return Optional.ofNullable(principal.toString());
        }
    }
}
