package com.techisgood.carrentals.comms.twilio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum TwilioChannels {
    SMS("sms"), EMAIL("email");

    private final String channel;
}
