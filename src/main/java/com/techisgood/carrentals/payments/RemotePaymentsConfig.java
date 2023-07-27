package com.techisgood.carrentals.payments;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RemotePaymentsConfig {
	private final RemotePaymentsProperties props;
	
	@PostConstruct
	public void init() {
		Stripe.apiKey = props.getSecretKeyTest();
	}
}
