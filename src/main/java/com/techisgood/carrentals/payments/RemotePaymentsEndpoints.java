package com.techisgood.carrentals.payments;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.stripe.model.Event;

@Controller
public class RemotePaymentsWebhooksController {

	private void validateWebhook() {
		
	}
	
	@PostMapping(path = {"/payments/stripe-webhook"})
    public void stripeWebhook(@RequestBody String requestBody) {
        
		//Event stripeEvent = 
		
    }
	
}
