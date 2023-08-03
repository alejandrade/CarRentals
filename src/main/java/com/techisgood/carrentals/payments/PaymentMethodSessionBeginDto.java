package com.techisgood.carrentals.payments;

import com.stripe.model.checkout.Session;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentMethodSessionBeginDto {

	@NotBlank(message = "userId is required")
	public String userId;
	
	public String url;
	public String successUrl;
	public String cancelUrl;
	
	public static PaymentMethodSessionBeginDto from(String userId, Session session) {
		PaymentMethodSessionBeginDto result = new PaymentMethodSessionBeginDto();
		result.userId     = userId;
		result.url        = session.getUrl();
		result.successUrl = session.getSuccessUrl();
		result.cancelUrl  = session.getCancelUrl();
		return result;
	}
	
}
