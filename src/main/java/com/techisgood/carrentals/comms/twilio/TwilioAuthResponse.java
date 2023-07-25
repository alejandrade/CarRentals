package com.techisgood.carrentals.comms.twilio;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TwilioAuthResponse {
    private boolean verified;
    private String token;
}
