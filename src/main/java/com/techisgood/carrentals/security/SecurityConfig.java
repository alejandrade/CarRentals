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
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
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
                .cors(CorsConfigurer::disable)
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class); // adding JWT filter
        return http.build();
    }


@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));  // Allow all origins
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // Allow all methods
    configuration.setAllowedHeaders(Arrays.asList("*"));  // Allow all headers
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration); // Apply the CORS configuration on all paths
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
