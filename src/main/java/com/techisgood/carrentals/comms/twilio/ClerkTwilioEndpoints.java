package com.techisgood.carrentals.comms.twilio;

import com.techisgood.carrentals.authorities.UserAuthority;
import com.techisgood.carrentals.user.UserCreateIfNotExistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clerk/v1/twilio")
@PreAuthorize("hasAuthority('ROLE_CLERK') || hasAuthority('ROLE_ADMIN')")
public class ClerkTwilioEndpoints {
    private final TwilioService twilioService;
    private final UserCreateIfNotExistService userCreateIfNotExistService;

    @PostMapping("/verify")
    public ResponseEntity<?> verifyTwilioCode(@Valid @RequestBody TwilioVerificationDto dto) {
        var resp = twilioService.verify(dto.getCode(), dto.getPhoneNumber());
        if (resp.isVerified()) {
            String contact = dto.getPhoneNumber() != null
                    ? dto.getPhoneNumber()
                    : dto.getEmail();
            userCreateIfNotExistService.createIfNoneExists(contact, UserAuthority.ROLE_USER);
            // Create an authentication token and set it in the security context
            return ResponseEntity.ok(Map.of("verified", true));
        }

        return ResponseEntity.ok(Map.of("verified", false));
    }
}
