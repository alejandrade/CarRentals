package com.techisgood.carrentals.comms.twilio;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TwilioDto {
    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^\\+\\d{1,15}$", message = "Phone number format is invalid")
    private String phoneNumber;

    @NotNull(message = "Channel is required")
    private TwilioChannels channel;
}
