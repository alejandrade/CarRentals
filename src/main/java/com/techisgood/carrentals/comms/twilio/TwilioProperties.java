package com.techisgood.carrentals.comms.twilio;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TwilioProperties {
    @Value("${comms.twilio.auth-token}")
    private String authToken;
    @Value("${comms.twilio.sid}")
    private String sid;
    @Value("${comms.twilio.service}")
    private String service;

    @Value("${comms.twilio.message-service}")
    private String messageService;

    @Value("${comms.twilio.debug}")
    private boolean debug;
}
