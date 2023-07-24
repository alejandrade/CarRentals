package com.techisgood.carrentals.payments;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class PaymentsProperties {
	@Value("${stripe.secret-key}")
	private String secretKey;
	
	@Value("${stripe.secret-key-test}")
	private String secretKeyTest;
}
