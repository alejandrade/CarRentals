package com.techisgood.carrentals.comms.twilio;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class TwilioVerificationCheckResponse {
    private String status;
    private String payee;

    @JsonProperty("date_updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    private ZonedDateTime dateUpdated;

    @JsonProperty("account_sid")
    private String accountSid;

    private String to;
    private Double amount; // Using Double since the example value is null but might be a numeric value in other cases
    private boolean valid;
    private String sid;

    @JsonProperty("date_created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    private ZonedDateTime dateCreated;

    @JsonProperty("service_sid")
    private String serviceSid;

    private String channel;
}
