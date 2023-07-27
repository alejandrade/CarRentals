package com.techisgood.carrentals.comms.twilio;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TwilioVerifyResponse {
    private boolean verified;
    private String error;
    private int code;
}
