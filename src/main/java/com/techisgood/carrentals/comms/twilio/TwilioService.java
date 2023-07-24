package com.techisgood.carrentals.comms.twilio;

import com.techisgood.carrentals.exception.InvalidPhoneNumberException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.techisgood.carrentals.comms.twilio.TwilioConfig.SERVICE_ID;

@RequiredArgsConstructor
@Service
@Slf4j
public class TwilioService {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+\\d{1,15}$");

    public void sendVerification(String phoneNumber, TwilioChannels channels) throws InvalidPhoneNumberException {
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        if (!matcher.matches()) {
            throw new InvalidPhoneNumberException("Invalid phone number format: " + phoneNumber);
        }

        Verification verification =
                Verification.creator(SERVICE_ID,
                phoneNumber,
                        channels.getChannel())
                .create();

        if (!verification.getValid()) {
            throw new InvalidPhoneNumberException(verification.getStatus());
        }

        log.info(verification.getStatus());
    }

    public boolean verify(String code, String phoneNumber) throws InvalidPhoneNumberException {
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        if (!matcher.matches()) {
            throw new InvalidPhoneNumberException("Invalid phone number format: " + phoneNumber);
        }

        VerificationCheck verificationCheck =
                VerificationCheck.creator(SERVICE_ID)
                        .setTo(phoneNumber)
                        .setCode(code)
                        .create();


        return Objects.equals(verificationCheck.getStatus(), "approved");
    }





}
