package com.techisgood.carrentals.payments;

import com.techisgood.carrentals.model.InvoiceType;
import com.techisgood.carrentals.model.PaymentsInvoice;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentStatementsRow {
    private String id;
    private String rentalId;
    private String payerId;
    private Integer subTotal;
    private Double taxRate;
    private Integer taxTotal;
    private Integer total;
    private String paidBy;
    private String location;
    private InvoiceType invoiceType;

    // Audit properties
    private LocalDateTime createdAt;


    public static PaymentStatementsRow from(PaymentsInvoice invoice, String serviceLocationName) {
        PaymentStatementsRow result = new PaymentStatementsRow();

        result.id = invoice.getId();
        result.rentalId = invoice.getRental().getId();
        result.payerId = invoice.getPayer().getId();
        result.subTotal = invoice.getSubTotal();
        result.taxRate = invoice.getTaxRate().doubleValue();
        result.taxTotal = invoice.getTaxTotal();
        result.total = invoice.getTotal();
        result.invoiceType = invoice.getInvoiceType();
        if (invoice.getPaidBy() != null) {
            result.paidBy = invoice.getPaidBy().getId();
        }

        result.location = serviceLocationName;
        result.createdAt = invoice.getCreatedAt();

        return result;
    }
}
