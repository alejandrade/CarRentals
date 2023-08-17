package com.techisgood.carrentals.payments;

import com.techisgood.carrentals.model.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techisgood.carrentals.model.PaymentsInvoice;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaymentsInvoiceRepository extends JpaRepository<PaymentsInvoice, String> {

    @Query("SELECT i FROM PaymentsInvoice i WHERE i.payer.id = :payerId AND i.rental.id = :rentalId AND i.invoiceType = :type")
    Optional<PaymentsInvoice> findByRentIdAndPayerId(String rentalId, String payerId, InvoiceType type);

    @Query("SELECT i FROM PaymentsInvoice i WHERE i.payer.id = :payerId and i.paidBy is null")
    List<PaymentsInvoice> activeInvoiceByPayerId(String payerId);

}
