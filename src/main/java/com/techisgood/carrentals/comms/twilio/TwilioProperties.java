package com.techisgood.carrentals.comms.twilio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Getter
@Component
@RequiredArgsConstructor
public class TwilioProperties {
    private String authToken;
    private String sid;
    private String service;

    @Value("${comms.twilio.message-service}")
    private String messageService;

    @Value("${comms.twilio.debug}")
    private boolean debug;

    private final SecretsManagerClient secretsManagerClient;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() throws JsonProcessingException {
        GetSecretValueRequest apiSecretsArc = GetSecretValueRequest.builder().secretId("API_SECRETS_ARC").build();
        GetSecretValueResponse secretValue = secretsManagerClient.getSecretValue(apiSecretsArc);
        JsonNode jsonNode = objectMapper.readValue(secretValue.secretString(), JsonNode.class);
        authToken = jsonNode.get("TWILIO_AUTH_TOKEN").asText();
        sid = jsonNode.get("TWILIO_SID").asText();
        service = jsonNode.get("TWILIO_SERVICE").asText();
    }
}
