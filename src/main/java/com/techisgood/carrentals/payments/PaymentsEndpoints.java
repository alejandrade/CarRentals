package com.techisgood.carrentals.payments;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments/v1")
@RequiredArgsConstructor
public class PaymentsEndpoints {

	private final RemotePaymentsService remotePaymentsService;
	
	
	public ResponseEntity<?> createInvoice(@Valid @RequestBody PaymentsInvoiceCreateDto requestBody) {
		
		return ResponseEntity.ok().body("");
	}
	
	
	@GetMapping("/test-tax")
	public ResponseEntity<?> testTax() {
		try {
			remotePaymentsService.calculateTaxRate("113 Claymore Dr.", "Huntsville", "AL", "35811", 1502);
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("");
	}
	
}
