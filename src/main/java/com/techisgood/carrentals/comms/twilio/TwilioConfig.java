package com.techisgood.carrentals.comms.twilio;

import com.twilio.Twilio;
import com.twilio.rest.sync.v1.Service;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class TwilioConfig {
    private final TwilioProperties props;
    public static String SERVICE_ID = "";

    @PostConstruct
    public void init() {
        Twilio.init(props.getSid(), props.getAuthToken());
        Service service = Service.creator().create();
        SERVICE_ID = service.getSid();
    }
}
