package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.Authority;
import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        DbUser dbUser = userRepository.getReferenceById(input);
        List<Authority> authorities = dbUser.getAuthorities();
        List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
                .collect(Collectors.toList());

        String identifier = dbUser.getIsEmailAuth() ? dbUser.getEmail() : dbUser.getPhoneNumber();

        return new User(identifier,
                "", // No password here as per your earlier decision
                dbUser.getEnabled(),
                dbUser.getAccountNonExpired(),
                dbUser.getCredentialsNonExpired(),
                dbUser.getAccountNonLocked(),
                grantedAuthorities);
    }
}
