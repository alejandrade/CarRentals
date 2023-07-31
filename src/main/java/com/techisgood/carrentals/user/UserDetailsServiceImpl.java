package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.Authority;
import com.techisgood.carrentals.model.DbUser;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    @Cacheable(cacheNames = "users", key = "#userID")
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
        DbUser dbUser = userRepository.getReferenceById(userID);
        List<Authority> authorities = dbUser.getAuthorities();
        List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
                .collect(Collectors.toList());

        String identifier = dbUser.getId();

        return new User(identifier,
                Strings.EMPTY, // No password here as per your earlier decision
                dbUser.getEnabled(),
                dbUser.getAccountNonExpired(),
                dbUser.getCredentialsNonExpired(),
                dbUser.getAccountNonLocked(),
                grantedAuthorities);
    }
}
