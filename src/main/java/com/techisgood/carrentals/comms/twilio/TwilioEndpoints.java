package com.techisgood.carrentals.comms.twilio;

import com.techisgood.carrentals.exception.InvalidPhoneNumberException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
