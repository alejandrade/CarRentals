package com.techisgood.carrentals.payments;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techisgood.carrentals.model.PaymentsCustomer;

public interface PaymentsCustomerRepository extends JpaRepository<PaymentsCustomer, String> {
	Optional<PaymentsCustomer> findByUserId(String userId);
}
