package com.techisgood.carrentals.payments;

import com.stripe.Stripe;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Charge;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
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
	
	private final PaymentsService paymentsService;

	
	@PostMapping(path = {"/stripe-webhook"})
    public ResponseEntity<?> stripeWebhook(@Valid @RequestBody String requestBody, @RequestHeader("Stripe-Signature") String sigHeader) {
        
		Event event = null;
		try {
			event = Webhook.constructEvent(requestBody, sigHeader, props.getWebhookSecret());
		} catch(SignatureVerificationException e) {
			return ResponseEntity.status(401).body("Invalid Signature");
		}
		
		if (event == null) {
			return ResponseEntity.status(403).body("Invalid Event");
		}
		
		log.info("Processing event of type: "  + event.getType());
		log.info("request body: " + requestBody);
		
		EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
		StripeObject stripeObject = null;
		if (dataObjectDeserializer.getObject().isPresent()) {
			stripeObject = dataObjectDeserializer.getObject().get();
		}
		else {
			return ResponseEntity.status(400).body("Deserialization failed, possibly due to api version mismatch. got " + event.getApiVersion() + " expected " + Stripe.API_VERSION);
		}

		switch (event.getType()) {
			case "charge.succeeded" -> {
				Charge charge = (Charge) stripeObject;
				String paymentIntentId = charge.getPaymentIntent();
				//NOTE(justin): doesnt seem like we need this event.
				break;
			}
			case "checkout.session.completed" -> {
				Session session = (Session) stripeObject;
				String paymentIntentId = session.getPaymentIntent();
				String internalInvoiceId = session.getMetadata().get("internalInvoiceId");
				if (internalInvoiceId == null) {
					log.info("STRIPE EVENT CREATED FROM OTHER SOURCE, NOT UPDATING INVOICE");
					return ResponseEntity.ok("EVENT IGNORED");
				}
				paymentsService.updateInvoiceSetRemotePaymentInfo(internalInvoiceId, paymentIntentId, null);
				break;
			}
			case "payment_intent.created", "payment_intent.succeeded" -> {
				PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
				String paymentIntentId = paymentIntent.getId();
				String internalInvoiceId = paymentIntent.getMetadata().get("internalInvoiceId");
				if (internalInvoiceId == null) {
					log.info("STRIPE EVENT CREATED FROM OTHER SOURCE, NOT UPDATING INVOICE");
					return ResponseEntity.ok("EVENT IGNORED");
				}
				String paymentStatus = paymentIntent.getStatus();
				//possible statuses include : (requires_payment_method, requires_confirmation, requires_action, processing, requires_capture, canceled, or succeeded)
				//according to: https://stripe.com/docs/api/payment_intents/object
				paymentsService.updateInvoiceSetRemotePaymentInfo(internalInvoiceId, paymentIntentId, paymentStatus);
				break;
			}
			default -> {
				return ResponseEntity.ok().body("Unahndled event but sending 200 so that stripe knows the webhook endpoint is actually still working");
			}
		}
		
		return ResponseEntity.ok("EVENT CONSUMED"); 
		
    }
	
}
