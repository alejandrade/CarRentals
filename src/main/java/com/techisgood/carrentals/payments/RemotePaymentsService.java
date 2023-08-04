package com.techisgood.carrentals.payments;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.model.SetupIntent;
import com.stripe.model.checkout.Session;
import com.stripe.model.tax.Calculation;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentIntentCreateParams.ConfirmationMethod;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData.ProductData;
import com.stripe.param.checkout.SessionCreateParams.PaymentIntentData;
import com.techisgood.carrentals.model.DbUserDemographics;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RemotePaymentsService {

	
	
	public Customer createCustomer(DbUserDemographics userInfo) throws StripeException {
		CustomerCreateParams params = CustomerCreateParams.builder()
				.setName(userInfo.getFullNameFormatted())
				.putMetadata("user_id", userInfo.getUser().getId())
				.build();
		
		Customer customer = Customer.create(params);
		return customer;
	}
	
	public Customer updateCustomer(String customerId, DbUserDemographics userInfo) throws StripeException {
		Customer customer = getCustomerById(customerId);
		if (customer == null) {
			return createCustomer(userInfo);
		}
		else {
			CustomerUpdateParams params = CustomerUpdateParams.builder()
					.setName(userInfo.getFullNameFormatted())
					.putMetadata("user_id", userInfo.getUser().getId())
					.build();
			
			Customer updated = customer.update(params);
			return updated;
		}
	}
	
	public Customer getCustomerById(String customerId) throws StripeException {
		Customer customer = Customer.retrieve(customerId);
		if (customer.getDeleted() != null && customer.getDeleted()) return null;
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
	
	
	
	public PaymentsTaxInfo calculateTaxRate(String addressLine, String city, String state, String postalCode, Integer total) throws StripeException {
		PaymentsTaxInfo taxInfo = new PaymentsTaxInfo();

		
		
		HashMap<String, Object> address = new HashMap<>();
		address.put("line1", addressLine);
		address.put("line2", "");
		address.put("postal_code", postalCode);
		address.put("state", state);
		address.put("country", "US");
		HashMap<String, Object> customerDetails =
		  new HashMap<>();
		customerDetails.put("address", address);
		customerDetails.put("address_source", "billing");
		
		ArrayList<Object> lineItems = new ArrayList<>();
		HashMap<String, Object> lineItem1 = new HashMap<>();
		lineItem1.put("amount", total);
		lineItem1.put("reference", "Tax Rate Calculation");
		//NOTE(justin):  tax_code: txcd_10103001 (Software as a service (SaaS) - business use) refer to : https://stripe.com/docs/tax/tax-categories
		lineItem1.put("tax_code", "txcd_10103001"); 
		lineItems.add(lineItem1);
		
		
		HashMap<String, Object> params = new HashMap<>();
		params.put("currency", "usd");
		params.put("customer_details", customerDetails);
		params.put("line_items", lineItems);
		
		ArrayList<Object> expand = new ArrayList<>();
		expand.add("line_items");
		params.put("expand", expand);
		
		Calculation calculation  = Calculation.create(params);
		taxInfo.taxabilityReason = calculation.getTaxBreakdown().get(0).getTaxabilityReason();
		taxInfo.state            = calculation.getTaxBreakdown().get(0).getTaxRateDetails().getState();
		taxInfo.taxType          = calculation.getTaxBreakdown().get(0).getTaxRateDetails().getTaxType();
		
		String taxDecimalString = calculation.getTaxBreakdown().get(0).getTaxRateDetails().getPercentageDecimal();
		taxInfo.subTotal          = total;
		taxInfo.taxRatePercentage = Double.parseDouble(taxDecimalString) / 100.0;
		taxInfo.taxTotal          = (int) Math.round(total * taxInfo.taxRatePercentage);
		taxInfo.total             = taxInfo.subTotal + taxInfo.taxTotal;
		
		return taxInfo;
	}
	
	
	
	
	
	public Session setupPaymentMethodPrepareSession(String customerId, String successUrl, String cancelUrl) throws StripeException {
		SessionCreateParams params =
		SessionCreateParams.builder()
		.setMode(SessionCreateParams.Mode.SETUP)
	    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
	    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.US_BANK_ACCOUNT)
	    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CASHAPP)
	    .setCustomer(customerId)
	    .setSuccessUrl(successUrl + "?session_id={CHECKOUT_SESSION_ID}")
	    .setCancelUrl(cancelUrl)
	    .build();

		Session session = Session.create(params);
		
		return session;
		
	}
	

	
	public Session createCheckoutSession(String internalInvoiceId, String customerId, String successUrl, String cancelUrl, Integer total) throws StripeException {
		ProductData productData = ProductData.builder()
				.setName(customerId + " charge for " + (total/100.0))
				.build();
		PriceData priceData = PriceData.builder()
				.setUnitAmount(total.longValue())
				.setProductData(productData)
				.setCurrency("USD").build();
		LineItem item = LineItem.builder().
				setPriceData(priceData)
				.setQuantity(1L).build();
		
		//this is what links up the eventual payment with our internal invoice table.
		PaymentIntentData paymentIntentData = PaymentIntentData.builder().putMetadata("internalInvoiceId", internalInvoiceId).build();
		
		SessionCreateParams params = 
				SessionCreateParams.builder()
				.setMode(SessionCreateParams.Mode.PAYMENT)
			    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
			    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.US_BANK_ACCOUNT)
			    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CASHAPP)
				.setCustomer(customerId)
				.setPaymentIntentData(paymentIntentData)
				.putMetadata("internalInvoiceId", internalInvoiceId)
				.setSuccessUrl(successUrl + "?session_id={CHECKOUT_SESSION_ID}&success=true")
				.setCancelUrl(cancelUrl + "?canceled=true")
				.addLineItem(item)
		        .build();
		Session session = Session.create(params);
		log.info("Payment Intent: " + session.getPaymentIntent());
		return session;
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
