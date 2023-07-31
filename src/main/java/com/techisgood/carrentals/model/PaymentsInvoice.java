package com.techisgood.carrentals.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payments_invoice", catalog = "car_rentals")
@Getter
@Setter
public class PaymentsInvoice {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "char(36) default (uuid())", nullable = false)
	private String id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rental_id", nullable = false, foreignKey = @ForeignKey(name = "fk_payments_invoice_rentals"))
	private Rental rental;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_payments_invoice_users_payer"))
	private DbUser payer;
	
	@Column(name="day_price")
	private Integer dayPrice;

	@Column(name="days")
	private Integer days;
	
	@Column(name="sub_total")
	private Integer subTotal;
	
	@Column(name="tax_rate", precision=5, scale=4)
	private BigDecimal taxRate;
	
	@Column(name="tax_total")
	private Integer taxTotal;

	@Column(name="total")
	private Integer total;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paid_by", nullable = false, foreignKey = @ForeignKey(name = "fk_payments_invoice_users_paid"))
	private DbUser paidBy;
	
	

	@Column(name="external_payment_id", columnDefinition = "char(36)")
	private String externalPaymentId;
	
	@Column(name="external_payment_status", columnDefinition = "char(36)")
	private String externalPaymentStatus;
	
	
	@CreatedDate
    @Column(name = "created_at", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
	

    @CreatedBy
    @Column(name = "created_by", columnDefinition = "char(36)")
    private String createdBy;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;


    @LastModifiedBy
    @Column(name = "updated_by", columnDefinition = "char(36)")
    private String updatedBy;
    
    @Column(name = "active", columnDefinition = "tinyint default 1")
	private Byte active;
}
