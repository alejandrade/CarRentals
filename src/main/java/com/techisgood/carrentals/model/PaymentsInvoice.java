package com.techisgood.carrentals.model;

import java.math.BigDecimal;

import com.techisgood.carrentals.rentals.RentalPictureAngle;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.techisgood.carrentals.model.audit.Auditable;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payments_invoice", catalog = "car_rentals")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class PaymentsInvoice extends Auditable {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "char(36) default (uuid())", nullable = false)
	private String id;

	@Column(name="rental_id", columnDefinition = "char(36)", nullable = false)
	private String rentalId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rental_id", insertable=false, updatable=false, nullable = false, foreignKey = @ForeignKey(name = "fk_payments_invoice_rentals"))
	private Rental rental;

	@Column(name="payer_id", columnDefinition = "char(36)", nullable = false)
	private String payerId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payer_id", insertable=false, updatable=false, nullable = false, foreignKey = @ForeignKey(name = "fk_payments_invoice_users_payer"))
	private DbUser payer;

	@Column(name = "note")
	private String note;
	
	@Column(name="sub_total")
	private Integer subTotal;
	
	@Column(name="tax_rate", precision=5, scale=4)
	private BigDecimal taxRate;
	
	@Column(name="tax_total")
	private Integer taxTotal;

	@Column(name="total")
	private Integer total;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paid_by_id", insertable=false, updatable=false, nullable = false, foreignKey = @ForeignKey(name = "fk_payments_invoice_paid_by_id"))
	private DbUser paidBy;

	@Column(length = 50, name = "invoice_type")
	@Enumerated(EnumType.STRING)
	private InvoiceType invoiceType;

	@Column(name="external_payment_id", columnDefinition = "char(36)")
	private String externalPaymentId;
	
	@Column(name="external_payment_status", columnDefinition = "char(36)")
	private String externalPaymentStatus;
	
    
    @Column(name = "active", columnDefinition = "tinyint default 1")
	private Byte active;
}
