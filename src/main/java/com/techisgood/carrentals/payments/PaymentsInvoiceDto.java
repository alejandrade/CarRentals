package com.techisgood.carrentals.payments;

import java.time.LocalDateTime;

import com.techisgood.carrentals.model.PaymentsInvoice;

public class PaymentsInvoiceDto {
	private String id;
	private String rentalId;
	private String payerId;
	private Integer dayPrice;
	private Integer days;
	private Integer sub_total;
	private Double tax_rate; 
	private Integer tax_total;
	private Integer total;
	private String paid_by;
	private String external_payment_id;
	private String external_payment_status;
	
	// Audit properties
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    
    private Integer active;
    
    
    public static PaymentsInvoiceDto from(PaymentsInvoice invoice) {
    	PaymentsInvoiceDto result = new PaymentsInvoiceDto();
    	
    	
    	
    	return result;
    }
}
