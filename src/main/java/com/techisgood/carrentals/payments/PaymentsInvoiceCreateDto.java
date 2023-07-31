package com.techisgood.carrentals.payments;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentsInvoiceCreateDto {
	@NotBlank(message = "'rentalId' is required")
	private String rentalId;
	@NotBlank(message = "'dayPrice' is required")
	private Integer dayPrice;
	@NotBlank(message = "'days' is required")
	private Integer days;
}
