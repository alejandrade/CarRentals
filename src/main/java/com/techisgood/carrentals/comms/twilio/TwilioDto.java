package com.techisgood.carrentals.comms.twilio;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class TwilioDto {
    @Pattern(regexp = "^\\+\\d{1,15}$", message = "Phone number format is invalid")
    private String phoneNumber;

    @NotNull(message = "Channel is required")
    private TwilioChannels channel;

    @Email(message = "Email format is invalid")
    private String email;

    @AssertTrue(message = "Either phone number or email must be set")
    public boolean isEitherPhoneNumberOrEmailSet() {
        return phoneNumber != null || email != null;
    }
}
