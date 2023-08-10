package com.techisgood.carrentals.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;

import com.techisgood.carrentals.model.audit.VersionedAuditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="car_service_location_history", catalog="car_rentals")
public class CarServiceLocationHistory extends VersionedAuditable {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "char(36) default (uuid())")
	private String id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="car_id", nullable = false)
	private Car car;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id", nullable = false)
	private ServiceLocation serviceLocation;
	
	@Column(name="start_date")
	private LocalDateTime startDate;

	@Column(name="end_date")
	private LocalDateTime endDate;
	
	@Column(name="start_mileage")
	private BigDecimal startMileage;
	
	@Column(name="end_mileage")
	private BigDecimal endMileage;
}
