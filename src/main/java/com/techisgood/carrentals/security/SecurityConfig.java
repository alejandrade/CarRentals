package com.techisgood.carrentals.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.techisgood.carrentals.authorities.UserAuthority;
import com.techisgood.carrentals.user.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider tokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Permit all OPTIONS requests
                        .requestMatchers("/user/**").hasAnyAuthority(UserAuthority.ROLE_USER.name(), UserAuthority.ROLE_ADMIN.name())
                        .requestMatchers("/admin/**").hasAuthority(UserAuthority.ROLE_ADMIN.name())
                        .requestMatchers("/staff/**").hasAnyAuthority(UserAuthority.ROLE_STAFF.name(), UserAuthority.ROLE_ADMIN.name())
                        .requestMatchers("/clerk/**").hasAnyAuthority(UserAuthority.ROLE_CLERK.name(), UserAuthority.ROLE_ADMIN.name())
                        .requestMatchers("/serviceLocations/**").hasAnyAuthority(UserAuthority.ROLE_USER.name(), UserAuthority.ROLE_ADMIN.name())
                        .requestMatchers("/payments/**").hasAnyAuthority(UserAuthority.ROLE_USER.name(), UserAuthority.ROLE_ADMIN.name())
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/remote-payments/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().authenticated() // ensuring all other routes require authentication
                )
                .sessionManagement((sess) -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class); // adding JWT filter
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000", "http://192.168.86.30:3000", "https://autorentalsusa.com");
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://192.168.86.30:3000", "https://autorentalsusa.com"));  // <-- you might want to adjust this to allow any origin or specific ones
        configuration.applyPermitDefaultValues();
        configuration.setAllowCredentials(true);
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("PATCH");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("OPTIONS");
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Requestor-Type"));
        configuration.setExposedHeaders(Arrays.asList("X-Get-Header"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(HttpMethod.OPTIONS, "/**")
                .requestMatchers("/error")
                .requestMatchers("/actuator/**")
                .requestMatchers("/auth/**");
    }

}
