package com.techisgood.carrentals.payments;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.model.SetupIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentIntentCreateParams.ConfirmationMethod;
import com.techisgood.carrentals.model.DbUserDemographics;

@Component
public class PaymentsService {

	
	
	public Customer createCustomer(DbUserDemographics userInfo) throws StripeException {
		CustomerCreateParams params = CustomerCreateParams.builder()
				.setName(userInfo.getFullNameFormatted())
				.putMetadata("user_id", userInfo.getUser_id())
				.build();
		
		Customer customer = Customer.create(params);
		return customer;
	}
	
	
	
	public ArrayList<PaymentMethod> getPaymentMethodsForCustomer(String customerId) throws StripeException {
		HashMap<String, Object> params = new HashMap<>();
		params.put("customer", customerId);
		
		PaymentMethodCollection paymentMethods =
				  PaymentMethod.list(params);
		return (ArrayList<PaymentMethod>) paymentMethods.getData();
	}
	
	
	public PaymentIntent createFirstPaymentIntentForCustomer(String customerId, Long amount, String rentalId) throws StripeException {
		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
		.setCustomer(customerId)
	    .setAmount(amount)
	    .setCurrency("usd")
	    .addPaymentMethodType("card")
	    .setStatementDescriptor("CAR_RENTAL")
	    .putMetadata("rental_id", rentalId)
	    .build();
		
		PaymentIntent paymentIntent = PaymentIntent.create(params);
		return paymentIntent;
	}
	
	
	public PaymentIntent createPaymentIntentForCustomerWithExistingPaymentMethod(String customerId, Long amount, String rentalId, String paymentMethodId) throws StripeException {
		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
				.setCustomer(customerId)
			    .setAmount(amount)
			    .setCurrency("usd")
			    .setPaymentMethod(paymentMethodId)
			    .setConfirm(true)
			    .setConfirmationMethod(ConfirmationMethod.AUTOMATIC)
			    .setOffSession(true)
			    .setStatementDescriptor("CAR_RENTAL")
			    .putMetadata("rental_id", rentalId)
			    .build();
		
		PaymentIntent paymentIntent = PaymentIntent.create(params);
		return paymentIntent;
	}
	
	
	
	
	
	
	
	
	public enum PaymentMethodType {
		CARD("card"),
		US_BANK("us_bank_account"),
		;
		private String name;
		private PaymentMethodType(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}
	
	public SetupIntent createPaymentMethod(String customerId, PaymentMethodType type) throws StripeException {
		ArrayList<Object> paymentMethodTypes =
				  new ArrayList<>();
		paymentMethodTypes.add(type.getName());
		HashMap<String, Object> params = new HashMap<>();
		params.put(
		  "payment_method_types",
		  paymentMethodTypes
		);

		SetupIntent setupIntent =
		  SetupIntent.create(params);
		
		return setupIntent;
		
	}
	
	
	public void verifyMicrodeposits(String setupIntentId, Integer centsA, Integer centsB) throws StripeException {
		SetupIntent setupIntent =
		  SetupIntent.retrieve(setupIntentId);

		ArrayList<Object> amounts = new ArrayList<>();
		amounts.add(centsA);
		amounts.add(centsB);
		HashMap<String, Object> params = new HashMap<>();
		params.put("amounts", amounts);

		SetupIntent updatedSetupIntent =
		  setupIntent.verifyMicrodeposits(params);
	}
	
}
