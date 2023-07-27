package com.techisgood.carrentals.payments;

import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.model.PaymentsCustomer;
import com.techisgood.carrentals.model.PaymentsInvoice;
import com.techisgood.carrentals.model.Rental;
import com.techisgood.carrentals.rentals.RentalRepository;
import com.techisgood.carrentals.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentsService {
	
	private final PaymentsCustomerRepository paymentsCustomerRepository;
	private final PaymentsInvoiceRepository paymentsInvoicerRepository;
	
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
	public PaymentsInvoice createInvoice(String rentalId, String payerId, Integer dayPrice, Integer days) throws IllegalArgumentException {
		
		Optional<Rental> rental = rentalRepository.findById(rentalId);
		if (rental.isEmpty()) {
			throw new IllegalArgumentException("rental_id");
		}
		Optional<DbUser> payer = userRepository.findById(payerId);
		if (payer.isEmpty()) {
			throw new IllegalArgumentException("payer_id");
		}
		
		PaymentsInvoice pi = new  PaymentsInvoice();
		Integer total = dayPrice * days;
		pi.setRental(rental.get());
		pi.setPayer(payer.get());
		pi.setDayPrice(dayPrice);
		pi.setDays(days);
		pi.setTotal(total);
		
		paymentsInvoicerRepository.save(pi);
		
		return pi;
	}
	
}
