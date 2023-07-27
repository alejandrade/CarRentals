package com.techisgood.carrentals.payments;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.model.PaymentsCustomer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentsService {
	private final PaymentsCustomerRepository paymentsCustomerRepository;
	
	public PaymentsCustomer getCustomerByUserId(String userId) {
		Optional<PaymentsCustomer> pc = paymentsCustomerRepository.findByUserId(userId);
		return pc.orElse(null);
	}
	
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
}
