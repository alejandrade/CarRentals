package com.techisgood.carrentals.security;

import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.user.UserRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserRepository userRepository;
    private final String jwtSecret = "yourSecretKey";  // Replace with your secret key
    private final long jwtExpirationInMs = 3153600000000L;  // 1 hour for example

    public String generateToken(Authentication authentication) {
        DbUser userPrincipal = (DbUser) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userPrincipal.getId())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            // Handle token parsing exceptions
            // ...
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        String userId = claims.getSubject();

        // Load user details from the database
        DbUser dbUser = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        UserDetails userDetails = UserPrincipal.create(dbUser);  // Assuming you have a UserPrincipal class that implements UserDetails

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}
