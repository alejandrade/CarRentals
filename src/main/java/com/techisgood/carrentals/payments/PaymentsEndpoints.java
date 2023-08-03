package com.techisgood.carrentals.payments;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.techisgood.carrentals.exception.RemoteServiceException;
import com.techisgood.carrentals.exception.RemoteServiceException.RemoteService;
import com.techisgood.carrentals.model.PaymentsCustomer;
import com.techisgood.carrentals.model.PaymentsInvoice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments/v1")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_CLERK') || hasAuthority('ROLE_ADMIN')")
public class PaymentsEndpoints {

	private final RemotePaymentsService remotePaymentsService;
	private final PaymentsService paymentsService;
	
	@PostMapping("/invoices")
	public ResponseEntity<?> createInvoice(@Valid @RequestBody PaymentsInvoiceCreateDto requestBody) throws RemoteServiceException {
		try {
			PaymentsInvoice invoice = paymentsService.createInvoice(requestBody.getRentalId(), requestBody.getPayerId(), requestBody.getDayPrice(), requestBody.getDays());
			PaymentsInvoiceDto response = PaymentsInvoiceDto.from(invoice);
			return ResponseEntity.ok().body(response);
		} catch (StripeException e) {
			throw new RemoteServiceException(RemoteService.STRIPE, e.getMessage());
		}
	}
	
	@PostMapping("/invoices/createPayment/TestWithLoggedInUser")
	public ResponseEntity<?> invoiceCreatePayment(@Valid @RequestBody PaymentsInvoiceCreatePaymentDto requestBody, @AuthenticationPrincipal UserDetails auth) throws RemoteServiceException {
		requestBody.setUserId(auth.getUsername());
		requestBody.setInvoiceId("2b4184e0-a178-4848-b90c-4d752149b502");
		return invoiceCreatePayment(requestBody);
	}
	
	@PostMapping("/invoices/createPayment")
	public ResponseEntity<?> invoiceCreatePayment(@Valid @RequestBody PaymentsInvoiceCreatePaymentDto requestBody) throws RemoteServiceException {
		try {
			PaymentsCustomer customer = paymentsService.getCustomerByUserId(requestBody.getUserId());
			String paymentUrl = paymentsService.getUrlForPayment(requestBody.getInvoiceId(), customer.getCustomerId(), requestBody.getSuccessUrl(), requestBody.getCancelUrl());
			requestBody.url = paymentUrl;
			return ResponseEntity.ok().body(requestBody);
		} catch (StripeException e) {
			throw new RemoteServiceException(RemoteService.STRIPE, e.getMessage());
		}
	}
	


	@PostMapping("/paymentMethod/sessionBegin/TestWithLoggedInUser")
	public ResponseEntity<?> createPaymentMethodSessionBegin(@Valid @RequestBody PaymentMethodSessionBeginDto requestBody, @AuthenticationPrincipal UserDetails auth) throws RemoteServiceException {
		requestBody.setUserId(auth.getUsername());
		return createPaymentMethodSessionBegin(requestBody);
	}
	
	@PostMapping("/paymentMethod/sessionBegin")
	public ResponseEntity<?> createPaymentMethodSessionBegin(@Valid @RequestBody PaymentMethodSessionBeginDto requestBody) throws RemoteServiceException {
		try {
			PaymentsCustomer pc = paymentsService.getCustomerByUserId(requestBody.getUserId());
			ResponseEntity<?> result = null;
			if (pc == null) {
				result = ResponseEntity.ok().body(null);
			}
			else {
				Session checkoutSession = remotePaymentsService.setupPaymentMethodPrepareSession(
						pc.getCustomerId(), 
						requestBody.getSuccessUrl(), 
						requestBody.getCancelUrl());
				requestBody.url = checkoutSession.getUrl();
				
				result = ResponseEntity.ok().body(requestBody);
			}
			
			return result; 
		} catch (StripeException e) {
			throw new RemoteServiceException(RemoteService.STRIPE, e.getMessage());
		}
	}
	
	
	@GetMapping("/test-tax")
	public ResponseEntity<?> testTax(@RequestParam String address, @RequestParam String city, @RequestParam String state, @RequestParam String postalCode, @RequestParam Integer total) {
		try {
			PaymentsTaxInfo taxInfo = remotePaymentsService.calculateTaxRate(address, city, state, postalCode, total);
			return ResponseEntity.ok().body("Tax Info: subTotal: " + taxInfo.subTotal + 
					", taxRate: " + taxInfo.taxRatePercentage + 
					", taxTotal:" + taxInfo.taxTotal + 
					", total: " + taxInfo.total + 
					", state:" + taxInfo.state +
					", taxType:" + taxInfo.taxType +
					", taxabilityReason:" + taxInfo.taxabilityReason);
			
		} catch (StripeException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
}
