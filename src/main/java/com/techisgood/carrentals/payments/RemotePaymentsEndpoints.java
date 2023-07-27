package com.techisgood.carrentals.payments;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonSyntaxException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("remote-payments/v1")
@RequiredArgsConstructor
@Slf4j
public class RemotePaymentsEndpoints {


	private final RemotePaymentsProperties props;
	
	
	@PostMapping(path = {"/stripe-webhook"})
    public ResponseEntity<?> stripeWebhook(@Valid @RequestBody String requestBody, @RequestHeader("Stripe-Signature") String sigHeader) {
        
		Event event = null;
		try {
			event = Webhook.constructEvent(requestBody, sigHeader, props.getWebhookSecret());
		} catch(JsonSyntaxException e) {
			return ResponseEntity.badRequest().body("Malofrmed JSON");
		} catch(SignatureVerificationException e) {
			return ResponseEntity.status(401).body("Invalid Signature");
		}
		
		if (event == null) {
			return ResponseEntity.status(403).body("Invalid Event");
		}
		
		log.debug("Processing event of type: "  + event.getType());
		
		EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
		StripeObject stripeObject = null;
		if (dataObjectDeserializer.getObject().isPresent()) {
			stripeObject = dataObjectDeserializer.getObject().get();
		}
		else {
			return ResponseEntity.status(400).body("Deserialization failed, possibly due to api version mismatch.");
		}
		
		switch(event.getType()) {
			case "payment_intent.succeeded": 
			{
				PaymentIntent paymentIntent = (PaymentIntent)stripeObject;
				
			}
			break;
			default: return ResponseEntity.status(200).body("Unahndled event but sending 200 so that stripe knows the webhook endpoint is actually still working");
		}
		
		return ResponseEntity.ok("WORKED"); 
		
    }
	
}
