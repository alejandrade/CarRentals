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
	@NotBlank(message = "'payerId' is required")
	private String payerId;
	@NotNull(message = "'dayPrice' is required")
	private Integer dayPrice;
	@NotNull(message = "'days' is required")
	private Integer days;
}
