package com.techisgood.carrentals.payments;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.techisgood.carrentals.comms.twilio.TwilioService;
import com.techisgood.carrentals.model.*;
import com.techisgood.carrentals.service_location.ServiceLocationRepository;
import com.techisgood.carrentals.service_location_clerk.ServiceLocationClerkRepository;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
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
	private final ServiceLocationClerkRepository serviceLocationClerkRepository;
	private final ServiceLocationRepository serviceLocationRepository;
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
		builder.append("Thanks for Renting with us click link to pay your fees ");
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

	public List<PaymentsInvoice> getInvoiceBySession(UserDetails auth) {
		String userId = auth.getUsername();
		return paymentsInvoiceRepository.activeInvoiceByPayerId(userId);
	}
	
	@Transactional
	public PaymentsInvoice createInvoice(
			String rentalId,
			String payerId,
			Integer subTotal,
			String note,
			InvoiceType type) throws IllegalArgumentException, StripeException {

		//don't create invoice twice
		Optional<PaymentsInvoice> byRentIdAndPayerId = paymentsInvoiceRepository
				.findByRentIdAndPayerId(rentalId, payerId, type);
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
		pi.setInvoiceType(type);
		
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

	@Transactional
	public List<PaymentStatementsRow> createStatement(Date startDate) {
		LocalDateTime localDateTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		List<CustomPaymentLocationDTO> customPaymentsWithLocationData = paymentsInvoiceRepository.findCustomPaymentsWithLocationData(localDateTime);
        return customPaymentsWithLocationData.stream().map(x -> PaymentStatementsRow.from(x.getPayment(), x.getLocationName())).toList();
	}
	
	
	
}
