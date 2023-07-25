package com.techisgood.carrentals.model.audit;

import com.techisgood.carrentals.user.UserByEmailOrPhoneService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider(UserByEmailOrPhoneService userContextService) {
        return new AuditorAwareImpl(userContextService);
    }
}

