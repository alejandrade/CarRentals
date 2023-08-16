package com.techisgood.carrentals.payments;

import java.math.BigDecimal;
import java.util.Optional;

import com.techisgood.carrentals.comms.twilio.TwilioService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.model.PaymentsCustomer;
import com.techisgood.carrentals.model.PaymentsInvoice;
import com.techisgood.carrentals.model.Rental;
import com.techisgood.carrentals.model.ServiceLocation;
import com.techisgood.carrentals.rentals.RentalRepository;
import com.techisgood.carrentals.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentsService {
	
	private final RemotePaymentsService remotePaymentsService;
	private final PaymentsCustomerRepository paymentsCustomerRepository;
	private final PaymentsInvoiceRepository paymentsInvoiceRepository;
	
	private final RentalRepository rentalRepository;
	private final UserRepository userRepository;
	private final TwilioService twilioService;
	
	public PaymentsCustomer getCustomerByUserId(String userId) {
		Optional<PaymentsCustomer> pc = paymentsCustomerRepository.findByUserId(userId);
		return pc.orElse(null);
	}

	@SneakyThrows
	public void billRenter(String rentalId, String paymentUrl) {
		Rental rental = rentalRepository.findById(rentalId).orElseThrow();
		DbUser renter = rental.getRenter();
		StringBuilder builder = new StringBuilder();

		Integer cleaningFee = rental.getCleaningFee();
		Integer damagedFee = rental.getDamagedFee();
		Integer insuranceFee = rental.getInsuranceFee();
		Integer gasFee = rental.getGasFee();
		builder.append("Thanks for Renting with us\n");
		buildMessage(builder, cleaningFee, "Cleaning Fee");
		buildMessage(builder, damagedFee, "Damage Fee");
		buildMessage(builder, insuranceFee, "Insurance Fee");
		buildMessage(builder, gasFee, "Gas Fee");
		builder.append(paymentUrl);
		twilioService.sendMessage(renter.getPhoneNumber(), builder.toString());
	}

	private void buildMessage(StringBuilder builder, Integer cleaningFee, String label) {
		if (cleaningFee != null && cleaningFee > 0)
			builder.append(label + ": " + cleaningFee + "\n");
	}


	@Transactional
	public PaymentsCustomer createCustomer(DbUser user, String customerId) {
		Optional<PaymentsCustomer> opc = paymentsCustomerRepository.findByUserId(user.getId());
		if (opc.isPresent()) {
			return opc.get();
		}
		else {
			PaymentsCustomer pc = new PaymentsCustomer();
			pc.setUser(user);
			pc.setCustomerId(customerId);
			paymentsCustomerRepository.save(pc);
			return pc;
		}
	}
	
	@Transactional
	public PaymentsCustomer updateCustomer(String userId, String customerId) {
		Optional<PaymentsCustomer> opc = paymentsCustomerRepository.findByUserId(userId);
		if (opc .isPresent()) {
			PaymentsCustomer pc = opc.get();
			pc.setCustomerId(customerId);
			paymentsCustomerRepository.save(pc);
			return pc;
		}
		return null;
	}
	
	
	@Transactional
	public PaymentsInvoice createInvoice(
			String rentalId, 
			String payerId, 
			Integer subTotal,
			String note) throws IllegalArgumentException, StripeException {

		//don't create invoice twice
		Optional<PaymentsInvoice> byRentIdAndPayerId = paymentsInvoiceRepository.findByRentIdAndPayerId(rentalId, payerId);
		if (byRentIdAndPayerId.isPresent()) {
			return byRentIdAndPayerId.get();
		}

		Optional<Rental> optionalRental = rentalRepository.findById(rentalId);
		if (optionalRental .isEmpty()) {
			throw new IllegalArgumentException("rental_id");
		}
		Optional<DbUser> optionalPayer = userRepository.findById(payerId);
		if (optionalPayer.isEmpty()) {
			throw new IllegalArgumentException("payer_id");
		}
		
		PaymentsInvoice pi = new  PaymentsInvoice();
		pi.setRentalId(rentalId);
		pi.setPayerId(payerId);
		pi.setNote(note);
		
		Rental rental = optionalRental.get();
		
		pi.setRental(rental);
		pi.setPayer(optionalPayer.get());
		
		ServiceLocation serviceLocation = rental.getServiceLocation();
		
		PaymentsTaxInfo taxInfo = remotePaymentsService.calculateTaxRate(
				serviceLocation.getAddress(), 
				serviceLocation.getCity(), 
				serviceLocation.getState(), 
				serviceLocation.getPostalCode(), 
				subTotal);
		pi.setSubTotal(taxInfo.subTotal);
		pi.setTaxRate(new BigDecimal(taxInfo.taxRatePercentage));
		pi.setTaxTotal(taxInfo.taxTotal);
		
		pi.setTotal(taxInfo.total);
		
		pi = paymentsInvoiceRepository.save(pi);
		
		return pi;
	}
	
	
	@Transactional 
	public PaymentsInvoice updateInvoiceSetRemotePaymentInfo(String invoiceId, String remotePaymentId, String remotePaymentStatus) {
		Optional<PaymentsInvoice> opi = paymentsInvoiceRepository.findById(invoiceId);
		if (opi.isEmpty()) return null;
		PaymentsInvoice pi = opi.get();
		if (pi.getExternalPaymentStatus() != null) {
			if (remotePaymentStatus == null) {
				return pi;
			}
			if (pi.getExternalPaymentStatus().equalsIgnoreCase("succeeded")) {
				return pi;
			}
		}
		
		pi.setExternalPaymentId(remotePaymentId);
		pi.setExternalPaymentStatus(remotePaymentStatus);
		pi = paymentsInvoiceRepository.save(pi);
		return pi;
	}
	
	
	
	public PaymentsInvoiceDto getInvoice(String invoiceId) {
		Optional<PaymentsInvoice> maybeInvoice = paymentsInvoiceRepository.findById(invoiceId);
		if (maybeInvoice.isEmpty()) return null;
		return PaymentsInvoiceDto.from(maybeInvoice.get());
	}
	
	@Transactional
	public String getUrlForPayment(String invoiceId, String customerId, String successUrl, String cancelUrl) throws StripeException {
		String result = null;
		
		PaymentsInvoiceDto invoice = getInvoice(invoiceId);
		Session session = remotePaymentsService.createCheckoutSession(invoiceId, customerId, successUrl, cancelUrl, invoice.getTotal());
		result = session.getUrl();
		
		return result;
	}
	
	
	
}
