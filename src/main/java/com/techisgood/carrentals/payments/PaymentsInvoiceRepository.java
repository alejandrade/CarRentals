package com.techisgood.carrentals.payments;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techisgood.carrentals.model.PaymentsInvoice;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaymentsInvoiceRepository extends JpaRepository<PaymentsInvoice, String> {

    @Query("SELECT i FROM PaymentsInvoice i WHERE i.payer.id = :payerId AND i.rental.id = :rentalId")
    Optional<PaymentsInvoice> findByRentIdAndPayerId(String rentalId, String payerId);
	
}
