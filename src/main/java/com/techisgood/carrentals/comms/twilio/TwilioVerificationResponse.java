package com.techisgood.carrentals.comms.twilio;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
@Data
public class TwilioVerificationResponse {
    private String status;
    private String payee; // if payee can have other types, use Object or a more specific type
    private ZonedDateTime date_updated;
    private List<SendCodeAttempt> send_code_attempts;
    private String account_sid;
    private String to;
    private String amount; // if amount can have other types, use Object or a more specific type
    private boolean valid;
    private Lookup lookup;
    private String url;
    private String sid;
    private ZonedDateTime date_created;
    private String service_sid;
    private String channel;
}
