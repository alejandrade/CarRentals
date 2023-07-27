package com.techisgood.carrentals.comms.twilio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techisgood.carrentals.exception.InvalidPhoneNumberException;
import com.techisgood.carrentals.user.UserNameValidator;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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

import static com.techisgood.carrentals.user.UserNameValidator.PHONE_PATTERN;

@RequiredArgsConstructor
@Service
@Slf4j
public class TwilioService {

    private final TwilioProperties properties;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public TwilioVerifyResponse sendVerification(String username, TwilioChannels channels) throws InvalidPhoneNumberException {
        if(properties.isDebug()) {
            return new TwilioVerifyResponse(true, "", 200);
        }

        boolean isEmailorPhone = UserNameValidator.isEmailOrPhoneNumber(username);
        if (!isEmailorPhone) {
            throw new InvalidPhoneNumberException("Invalid phone number or email format: " + username);
        }

        return sendHttpVerification(username, channels);
    }

    @SneakyThrows
    public TwilioVerifyResponse verify(String code, String username) throws InvalidPhoneNumberException {
        if (properties.isDebug()) {
            return new TwilioVerifyResponse(true, "", 200);
        }

        boolean isEmailorPhone = UserNameValidator.isEmailOrPhoneNumber(username);

        if (!isEmailorPhone) {
            return new TwilioVerifyResponse(false, "invalid phone number", 400);
        }

        var resp = checkVerificationCode(username, code);
        return new TwilioVerifyResponse(resp.isVerified(), "", 200);
    }


    @CircuitBreaker(name = "twilioStartVerificationApi", fallbackMethod = "checkVerificationCodeFallback" )
    @RateLimiter(name = "twilioStartVerificationApi", fallbackMethod = "checkVerificationCodeFallback"  )
    @Retry(name = "twilioStartVerificationApi", fallbackMethod = "checkVerificationCodeFallback" )
    @Bulkhead(name = "twilioStartVerificationApi", fallbackMethod = "checkVerificationCodeFallback")
    private TwilioVerifyResponse sendHttpVerification(String username, TwilioChannels channels) throws IOException, InterruptedException {
        String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8);

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
                .POST(HttpRequest.BodyPublishers.ofString(String.format("To=%s&Channel=%s", encodedUsername, channels.getChannel())))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() > 300) {
            log.error(response.body());
            return new TwilioVerifyResponse(false, response.body(), response.statusCode());
        }

        return new TwilioVerifyResponse(true, "", 200);
    }


    @CircuitBreaker(name = "twilioVerifyApi", fallbackMethod = "checkVerificationCodeFallback" )
    @RateLimiter(name = "twilioVerifyApi", fallbackMethod = "checkVerificationCodeFallback")
    @Retry(name = "twilioVerifyApi", fallbackMethod = "checkVerificationCodeFallback")
    @Bulkhead(name = "twilioVerifyApi", fallbackMethod = "checkVerificationCodeFallback")
    private TwilioVerifyResponse checkVerificationCode(String username, String verificationCode) throws IOException, InterruptedException {
        String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8);

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
                .POST(HttpRequest.BodyPublishers.ofString(String.format("Code=%s&To=%s", verificationCode, encodedUsername)))
                .build();

        // Send the request and parse the response
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() > 300) {
            log.error(response.body());
            return new TwilioVerifyResponse(false, response.body(), response.statusCode());
        }

        TwilioVerificationCheckResponse resp = objectMapper.readValue(response.body(), TwilioVerificationCheckResponse.class);
        return new TwilioVerifyResponse(Objects.equals(resp.getStatus(), "approved"), "", 200);
    }

    //Circuit breaker pattern
    private TwilioVerifyResponse checkVerificationCodeFallback() {
        return new TwilioVerifyResponse(false, "twilio api down", 500);
    }
}
