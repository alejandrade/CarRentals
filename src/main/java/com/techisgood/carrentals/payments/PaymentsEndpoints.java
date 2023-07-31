package com.techisgood.carrentals.payments;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.techisgood.carrentals.exception.RemoteServiceException;
import com.techisgood.carrentals.exception.RemoteServiceException.RemoteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments/v1")
@RequiredArgsConstructor
public class PaymentsEndpoints {

	private final RemotePaymentsService remotePaymentsService;
	private final PaymentsService paymentsService;
	
	@PostMapping("/invoices")
	public ResponseEntity<?> createInvoice(@Valid @RequestBody PaymentsInvoiceCreateDto requestBody) throws RemoteServiceException {
		try {
			paymentsService.createInvoice(requestBody.getRentalId(), requestBody.getPayerId(), requestBody.getDayPrice(), requestBody.getDays());
		} catch (StripeException e) {
			throw new RemoteServiceException(RemoteService.STRIPE, e.getMessage());
		}
		return ResponseEntity.ok().body("");
	}
	
	
	@GetMapping("/test-tax")
	public ResponseEntity<?> testTax(@RequestParam String address, @RequestParam String city, @RequestParam String state, @RequestParam String postalCode, @RequestParam Integer total) {
		try {
			PaymentsTaxInfo taxInfo = remotePaymentsService.calculateTaxRate(address, city, state, postalCode, total);
			return ResponseEntity.ok().body("Tax Info: subTotal: " + taxInfo.subTotal + ", taxRate: " + taxInfo.taxRatePercentage + ", taxTotal:" + taxInfo.taxTotal + ", total: " + taxInfo.total);
			
		} catch (StripeException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
}
