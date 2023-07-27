package com.techisgood.carrentals.comms.twilio;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
