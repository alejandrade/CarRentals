package com.techisgood.carrentals.comms.twilio;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TwilioAuthResponse {
    private boolean verified;
    private String token;
    private TwilioVerifyResponse twilioVerifyResponse;
    private List<String> authorities;
}
