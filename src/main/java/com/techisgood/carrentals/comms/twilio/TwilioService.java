package com.techisgood.carrentals.comms.twilio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techisgood.carrentals.exception.InvalidPhoneNumberException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@RequiredArgsConstructor
@Service
@Slf4j
public class TwilioService {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+\\d{1,15}$");

    private final TwilioProperties properties;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void sendVerification(String phoneNumber, TwilioChannels channels) throws InvalidPhoneNumberException {
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        if (!matcher.matches()) {
            throw new InvalidPhoneNumberException("Invalid phone number format: " + phoneNumber);
        }

        var verification = sendHttpVerification(phoneNumber, channels);

        if (!verification.isValid()) {
            throw new InvalidPhoneNumberException(verification.getStatus());
        }

        log.info(verification.getStatus());
    }

    @SneakyThrows
    public boolean verify(String code, String phoneNumber) throws InvalidPhoneNumberException {
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        if (!matcher.matches()) {
            throw new InvalidPhoneNumberException("Invalid phone number format: " + phoneNumber);
        }

        TwilioVerificationCheckResponse resp = checkVerificationCode(phoneNumber, code);


        return Objects.equals(resp.getStatus(), "approved");
    }


    //todo: hysterix
    private TwilioVerificationResponse sendHttpVerification(String phoneNumber, TwilioChannels channels) throws IOException, InterruptedException {
        String encodedPhoneNumber = URLEncoder.encode(phoneNumber, StandardCharsets.UTF_8);

        String auth = String.format("%s:%s", properties.getSid(), properties.getAuthToken());
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                                String.format(
                                        "https://verify.twilio.com/v2/Services/%s/Verifications", properties.getService())

                        )
                )
                .header("Authorization", "Basic " + encodedAuth)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .timeout(Duration.ofSeconds(2)) // Set the timeout to 2 seconds
                .POST(HttpRequest.BodyPublishers.ofString(String.format("To=%s&Channel=%s", encodedPhoneNumber, channels.getChannel())))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() > 300) {
            log.error(response.body());
            throw new RuntimeException();
        }

        return objectMapper.readValue(response.body(), TwilioVerificationResponse.class);
    }

    private TwilioVerificationCheckResponse checkVerificationCode(String phoneNumber, String verificationCode) throws IOException, InterruptedException {
        String encodedPhoneNumber = URLEncoder.encode(phoneNumber, StandardCharsets.UTF_8);

        // Build the authentication header using Base64 encoding
        String auth = String.format("%s:%s", properties.getSid(), properties.getAuthToken());
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        // Create the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        String.format(
                                "https://verify.twilio.com/v2/Services/%s/VerificationCheck", properties.getService())
                ))
                .header("Authorization", "Basic " + encodedAuth)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .timeout(Duration.ofSeconds(2)) // Set the timeout to 2 seconds
                .POST(HttpRequest.BodyPublishers.ofString(String.format("Code=%s&To=%s", verificationCode, encodedPhoneNumber)))
                .build();

        // Send the request and parse the response
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() > 300) {
            log.error(response.body());
            throw new RuntimeException();
        }
        return objectMapper.readValue(response.body(), TwilioVerificationCheckResponse.class);
    }





}
