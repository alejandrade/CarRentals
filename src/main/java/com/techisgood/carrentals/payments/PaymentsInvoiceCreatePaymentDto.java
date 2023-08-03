package com.techisgood.carrentals.payments;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentsInvoiceCreatePaymentDto {
	@NotBlank(message="userId is required")
	public String userId;
	@NotBlank(message="invoiceId is required")
	public String invoiceId;
	@NotBlank(message = "successUrl is required")
	public String successUrl;
	@NotBlank(message = "cancelUrl is required")
	public String cancelUrl;
	
	public String url;
}
