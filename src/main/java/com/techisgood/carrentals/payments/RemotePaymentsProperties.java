package com.techisgood.carrentals.payments;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class RemotePaymentsProperties {
	@Value("${stripe.secret-key}")
	private String secretKey;
	
	@Value("${stripe.secret-key-test}")
	private String secretKeyTest;
	
	@Value("${stripe.checkout-url-success}")
	private String checkoutUrlSuccess;
	
	@Value("${stripe.checkout-url-cancel}")
	private String checkoutUrlCancel;
	
	@Value("${stripe.webhook-secret}")
	private String webhookSecret;
}
