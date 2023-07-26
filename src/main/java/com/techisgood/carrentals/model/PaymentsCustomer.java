package com.techisgood.carrentals.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "payments_customer", catalog = "car_rentals")
@Getter
@Setter
public class PaymentsCustomer {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "char(36) default (uuid())", nullable = false)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_PAYMENTS_USER"))
	private DbUser user;

	@Column(name = "customer_id", length = 32)
	private String customerId;
}
