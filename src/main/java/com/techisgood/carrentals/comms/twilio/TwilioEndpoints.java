package com.techisgood.carrentals.comms.twilio;

import com.techisgood.carrentals.exception.InvalidPhoneNumberException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/v1/twilio")
public class TwilioEndpoints {
    private final TwilioService twilioService;

    @PostMapping("/startVerification")
    public ResponseEntity<?> processTwilioRequest(@Valid @RequestBody TwilioDto twilioRequest) throws InvalidPhoneNumberException {
        twilioService.sendVerification(twilioRequest.getPhoneNumber(), twilioRequest.getChannel());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Boolean>> verifyTwilioCode(@Valid @RequestBody TwilioVerificationDto dto) {
        boolean verify = twilioService.verify(dto.getCode(), dto.getPhoneNumber());
        return ResponseEntity.ok(Map.of("verified", verify));
    }
}
