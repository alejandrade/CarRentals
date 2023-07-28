package com.techisgood.carrentals.comms.twilio;


import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class TwilioVerificationDto {

    @Pattern(regexp = "^\\+\\d{1,15}$", message = "Phone number format is invalid")
    private String phoneNumber;

    @NotNull(message = "Verification code is required")
    @Pattern(regexp = "\\d+", message = "Verification code should only contain digits")
    private String code;

    @Email(message = "Email format is invalid")
    private String email;

    @AssertTrue(message = "Either phone number or email must be set")
    public boolean isEitherPhoneNumberOrEmailSet() {
        return phoneNumber != null || email != null;
    }

}