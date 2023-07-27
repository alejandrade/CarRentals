package com.techisgood.carrentals.model.audit;

import com.techisgood.carrentals.model.DbUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorProvider implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken  ||
                authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.empty();
        }

        DbUser userPrincipal = (DbUser) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getId());
    }
}