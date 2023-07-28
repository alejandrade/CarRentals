 package com.techisgood.carrentals.model.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.techisgood.carrentals.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuditorProvider implements AuditorAware<String> {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        String userIdFromJWT = jwtTokenProvider.getUserIdFromJWT(authentication.getCredentials().toString());
        return Optional.ofNullable(userIdFromJWT);
    }
}