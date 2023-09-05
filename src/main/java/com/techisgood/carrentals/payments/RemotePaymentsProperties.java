package com.techisgood.carrentals.payments;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Component
@Getter
@RequiredArgsConstructor
public class RemotePaymentsProperties {
	private String secretKey;

	private String webhookSecret;

	private final SecretsManagerClient secretsManagerClient;
	private final ObjectMapper objectMapper;

	@PostConstruct
	public void init() throws JsonProcessingException {
		GetSecretValueRequest apiSecretsArc = GetSecretValueRequest.builder().secretId("API_SECRETS_ARC").build();
		GetSecretValueResponse secretValue = secretsManagerClient.getSecretValue(apiSecretsArc);
		JsonNode jsonNode = objectMapper.readValue(secretValue.secretString(), JsonNode.class);
		secretKey = jsonNode.get("STRIPE_SECRET_KEY").asText();
		webhookSecret = jsonNode.get("STRIPE_WEBHOOK_SECRET").asText();
	}

//	@PostConstruct
//	@Profile("dev")
//	public void initForDev() throws JsonProcessingException {
//		GetSecretValueRequest apiSecretsArc = GetSecretValueRequest.builder().secretId("DEV_API_SECRETS_ARC").build();
//		GetSecretValueResponse secretValue = secretsManagerClient.getSecretValue(apiSecretsArc);
//		JsonNode jsonNode = objectMapper.readValue(secretValue.secretString(), JsonNode.class);
//		secretKey = jsonNode.get("STRIPE_SECRET_KEY").asText();
//		webhookSecret = "dev";
//	}
}
