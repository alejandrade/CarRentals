package com.techisgood.carrentals.model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payments_customer")
@Getter
@Setter
public class PaymentsCustomer {
	@Id private String id;
	@Column(nullable=false) private String userId;
	@Column(nullable=false) private String customerId;
}
