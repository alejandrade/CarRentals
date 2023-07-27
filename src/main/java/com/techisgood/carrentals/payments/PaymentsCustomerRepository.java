package com.techisgood.carrentals.payments;

import com.techisgood.carrentals.model.PaymentsCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentsCustomerRepository extends JpaRepository<PaymentsCustomer, String> {
	Optional<PaymentsCustomer> findByUserId(String userId);
}
