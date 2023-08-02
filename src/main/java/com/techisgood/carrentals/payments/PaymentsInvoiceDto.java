package com.techisgood.carrentals.payments;

import java.time.LocalDateTime;

import com.techisgood.carrentals.model.PaymentsInvoice;

import lombok.Data;

@Data
public class PaymentsInvoiceDto {
	private String id;
	private String rentalId;
	private String payerId;
	private Integer dayPrice;
	private Integer days;
	private Integer subTotal;
	private Double taxRate; 
	private Integer taxTotal;
	private Integer total;
	private String paidBy;
	private String externalPaymentId;
	private String externalPaymentStatus;
	
	// Audit properties
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    
    private Byte active;
    
    
    public static PaymentsInvoiceDto from(PaymentsInvoice invoice) {
    	PaymentsInvoiceDto result = new PaymentsInvoiceDto();
    	
    	result.id = invoice.getId();
    	result.rentalId = invoice.getRentalId();
    	result.payerId = invoice.getPayerId();
    	result.dayPrice = invoice.getDayPrice();
    	result.days = invoice.getDays();
    	result.subTotal = invoice.getSubTotal();
    	result.taxRate = invoice.getTaxRate().doubleValue();
    	result.taxTotal = invoice.getTaxTotal();
    	result.total = invoice.getTotal();
    	result.paidBy = invoice.getPaidById();
    	result.externalPaymentId = invoice.getExternalPaymentId();
    	result.externalPaymentStatus = invoice.getExternalPaymentStatus();
    	
    	result.createdAt = invoice.getCreatedAt();
    	result.createdBy = invoice.getCreatedBy();
    	result.updatedAt = invoice.getUpdatedAt();
    	result.updatedBy = invoice.getUpdatedBy();
    	
    	result.active = invoice.getActive();
    	
    	return result;
    }
}
