package com.techisgood.carrentals.payments;

import com.techisgood.carrentals.model.PaymentsInvoice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomPaymentLocationDTO {
    private PaymentsInvoice payment;
    private String locationName;
}
