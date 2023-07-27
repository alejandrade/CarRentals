package com.techisgood.carrentals.payments;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RemotePaymentsWebhookEventDataDto {
	private Object object;
}
