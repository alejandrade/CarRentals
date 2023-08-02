package com.techisgood.carrentals.payments;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
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
	
	public PaymentsCustomer getCustomerByUserId(String userId) {
		Optional<PaymentsCustomer> pc = paymentsCustomerRepository.findByUserId(userId);
		return pc.orElse(null);
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
	public PaymentsInvoice createInvoice(String rentalId, String payerId, Integer dayPrice, Integer days) throws IllegalArgumentException, StripeException {
		
		Optional<Rental> optionalRental = rentalRepository.findById(rentalId);
		if (optionalRental .isEmpty()) {
			throw new IllegalArgumentException("rental_id");
		}
		Optional<DbUser> optionalPayer = userRepository.findById(payerId);
		if (optionalPayer.isEmpty()) {
			throw new IllegalArgumentException("payer_id");
		}
		
		Rental rental = optionalRental.get();
		DbUser payer = optionalPayer.get();
		
		PaymentsInvoice pi = new  PaymentsInvoice();
		Integer subTotal = dayPrice * days;
		pi.setRental(rental);
		pi.setPayer(payer);
		pi.setDayPrice(dayPrice);
		pi.setDays(days);
		
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
	
}
