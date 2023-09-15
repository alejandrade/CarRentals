package com.techisgood.carrentals.payments;

import com.techisgood.carrentals.model.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techisgood.carrentals.model.PaymentsInvoice;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PaymentsInvoiceRepository extends JpaRepository<PaymentsInvoice, String> {

    @Query("SELECT i FROM PaymentsInvoice i WHERE i.payer.id = :payerId AND i.rental.id = :rentalId AND i.invoiceType = :type")
    Optional<PaymentsInvoice> findByRentIdAndPayerId(String rentalId, String payerId, InvoiceType type);

    @Query("SELECT i FROM PaymentsInvoice i WHERE i.payer.id = :payerId and i.paidBy is null")
    List<PaymentsInvoice> activeInvoiceByPayerId(String payerId);

    @Query("SELECT i FROM PaymentsInvoice i WHERE i.createdAt >= DATE_ADD(:startDate, 30) and i.paidBy is not null")
    List<PaymentsInvoice> findInvoicesCreatedAfter(Date startDate);

    // Add the custom query here
    @Query("SELECT p, sl.name AS location_name FROM PaymentsInvoice p JOIN ServiceLocationClerk slc ON p.paidBy.id = slc.user.id JOIN ServiceLocation sl ON slc.location.id = sl.id WHERE p.createdAt >= :startDate")
    List<CustomPaymentLocationDTO> findCustomPaymentsWithLocationData(Date startDate);

}
