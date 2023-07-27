package com.techisgood.carrentals.payments;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techisgood.carrentals.model.PaymentsInvoice;

public interface PaymentsInvoiceRepository extends JpaRepository<PaymentsInvoice, String> {
	
}
