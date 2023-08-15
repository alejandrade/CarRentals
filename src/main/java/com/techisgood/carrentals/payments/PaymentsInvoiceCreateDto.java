package com.techisgood.carrentals.payments;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentsInvoiceCreateDto {
	@NotBlank(message = "'rentalId' is required")
	private String rentalId;
	
	private Integer cleaningFee = 0;
	private Integer damageFee = 0;
	private Integer otherFee = 0;
}
