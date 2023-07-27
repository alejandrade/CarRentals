package com.techisgood.carrentals.comms.twilio;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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