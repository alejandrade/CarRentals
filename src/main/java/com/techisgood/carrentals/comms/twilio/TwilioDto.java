package com.techisgood.carrentals.comms.twilio;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class TwilioDto {
    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^\\+\\d{1,15}$", message = "Phone number format is invalid")
    private String phoneNumber;

    @NotNull(message = "Channel is required")
    private TwilioChannels channel;
}
