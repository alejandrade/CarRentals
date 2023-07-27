package com.techisgood.carrentals.payments;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RemotePaymentsWebhookEventDto {

	private String id;
	private String object;
	private String api_version;
	private Long created;
	private RemotePaymentsWebhookEventDataDto data;
	
}
