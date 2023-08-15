package com.techisgood.carrentals.payments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentsInvoiceCreateDto {
	@NotBlank(message = "'rentalId' is required")
	private String rentalId;

	@NotBlank(message = "payer required")
	private String payerId;

	@NotNull(message = "subtotal must not be null")
	private Integer subTotal = 0;

	@NotNull(message = "note must contain value")
	private String note;
}
