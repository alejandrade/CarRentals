package com.techisgood.carrentals.payments;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentsInvoiceCreateDto {
	private String rentalId;
	private Integer dayPrice;
	private Integer days;
}
