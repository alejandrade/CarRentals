package com.techisgood.carrentals.payments;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payments/v1")
public class PaymentsEndpoints {

	public ResponseEntity<?> createInvoice(@Valid @RequestBody PaymentsInvoiceCreateDto requestBody) {
		
		return ResponseEntity.ok().body("");
	}
	
}
