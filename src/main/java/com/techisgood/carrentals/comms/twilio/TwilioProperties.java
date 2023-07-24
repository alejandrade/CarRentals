package com.techisgood.carrentals.comms.twilio;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TwilioProperties {
    @Value("${comms.twilio.secret-key}")
    private String secretKey;
    @Value("${comms.twilio.auth-token}")
    private String authToken;
    @Value("${comms.twilio.sid}")
    private String sid;
}
