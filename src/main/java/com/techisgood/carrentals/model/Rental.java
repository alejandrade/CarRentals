package com.techisgood.carrentals.model;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "rentals", catalog = "car_rentals")
public class Rental {


	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "char(36) default (uuid())", nullable = false)
	private String id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "car_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rentals_cars"))
	private Car car;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "renter_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rentals_users"))
	private DbUser renter;
	
	
	@Column(name="initial_odometer_reading", columnDefinition="DECIMAL(10,2)", nullable=false)
	private Double initialOdometerReading;
	
	@Column(name="ending_odometer_reading", columnDefinition="DECIMAL(10,2)", nullable=false)
	private Double endingOdometerReading;
	
	@Column(name = "rental_datetime", columnDefinition = "timestamp", nullable=false)
	private LocalDateTime rentalDateTime;
	
	@Column(name = "return_datetime", columnDefinition = "timestamp")
	private LocalDateTime returnDateTime;
	
	
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
	
}
