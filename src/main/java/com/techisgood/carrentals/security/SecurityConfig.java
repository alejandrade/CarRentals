package com.techisgood.carrentals.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.CrossOriginEmbedderPolicyHeaderWriter;
import org.springframework.security.web.header.writers.CrossOriginOpenerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.CrossOriginResourcePolicyHeaderWriter;
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
                .cors(x -> {
                    x.configurationSource(corsConfigurationSource());
                })
                .requestCache(RequestCacheConfigurer::disable)
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class); // adding JWT filter
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow localhost with any port
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("https://app.arc.rent");
        configuration.addAllowedOrigin("https://autorentalsusa.com");

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // Allow all methods
        configuration.setAllowedHeaders(Arrays.asList("*"));  // Allow all headers
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply the CORS configuration on all paths
        return source;
    }

}
