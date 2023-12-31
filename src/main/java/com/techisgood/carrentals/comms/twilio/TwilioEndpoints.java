package com.techisgood.carrentals.comms.twilio;

import com.techisgood.carrentals.authorities.AuthorityRepository;
import com.techisgood.carrentals.authorities.UserAuthority;
import com.techisgood.carrentals.exception.InvalidPhoneNumberException;
import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.security.JwtTokenProvider;
import com.techisgood.carrentals.user.UserCreateIfNotExistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/v1/twilio")
public class TwilioEndpoints {
    private final TwilioService twilioService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserCreateIfNotExistService userCreateIfNotExistService;
    private final AuthorityRepository authorityRepository;

    @PostMapping("/startVerification")
    public ResponseEntity<TwilioVerifyResponse> processTwilioRequest(@Valid @RequestBody TwilioDto twilioRequest) throws InvalidPhoneNumberException {
        String contact = twilioRequest.getChannel().equals(TwilioChannels.SMS)
                ? twilioRequest.getPhoneNumber()
                : twilioRequest.getEmail();
        TwilioVerifyResponse twilioVerifyResponse = twilioService.sendVerification(contact, twilioRequest.getChannel());
        return ResponseEntity.ok(twilioVerifyResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<TwilioAuthResponse> verifyTwilioCode(@Valid @RequestBody TwilioVerificationDto dto) {
        var resp = twilioService.verify(dto.getCode(), dto.getPhoneNumber());
        if (resp.isVerified()) {
            String contact = dto.getPhoneNumber() != null
                    ? dto.getPhoneNumber()
                    : dto.getEmail();


            DbUser user = userCreateIfNotExistService.createIfNoneExists(contact, UserAuthority.ROLE_USER);
            // Create an authentication token and set it in the security context
            List<GrantedAuthority> authorities = authorityRepository.findByUserId(user.getId()) // hypothetical method to get roles
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                    .collect(Collectors.toList());
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
            String jwtToken = jwtTokenProvider.generateToken(auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
            return ResponseEntity.ok(new TwilioAuthResponse(true, jwtToken, resp,
                    authorities.stream().map(GrantedAuthority::getAuthority).toList()
                    ));

        }
        return ResponseEntity.ok(new TwilioAuthResponse(false, null, resp, Collections.emptyList()));
    }
}
