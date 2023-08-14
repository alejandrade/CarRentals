package com.techisgood.carrentals.payments;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RemotePaymentsProperties {
	@Value("${stripe.secret-key}")
	private String secretKey;
	
	@Value("${stripe.checkout-url-success}")
	private String checkoutUrlSuccess;
	
	@Value("${stripe.checkout-url-cancel}")
	private String checkoutUrlCancel;
	
	@Value("${stripe.webhook-secret}")
	private String webhookSecret;
}
