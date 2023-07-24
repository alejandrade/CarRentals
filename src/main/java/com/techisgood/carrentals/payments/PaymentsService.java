package com.techisgood.carrentals.payments;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.stripe.exception.StripeException;
import com.stripe.model.SetupIntent;
import com.stripe.param.CustomerCreateParams;
import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.model.DbUserDemographics;

@Component
public class PaymentsService {

	
	
	public void CreateCustomer(DbUserDemographics userInfo) {
		CustomerCreateParams params = CustomerCreateParams.builder()
				.setName(userInfo.getFullNameFormatted())
				.putMetadata("user_id", userInfo.getUser_id())
				.build(); 
	}
	
	
	public void CreatePaymentMethod(String customerId) throws StripeException {
		ArrayList<Object> paymentMethodTypes =
				  new ArrayList<>();
		paymentMethodTypes.add("card");
		HashMap<String, Object> params = new HashMap<>();
		params.put(
		  "payment_method_types",
		  paymentMethodTypes
		);

		SetupIntent setupIntent =
		  SetupIntent.create(params);
		
	}
	
}
