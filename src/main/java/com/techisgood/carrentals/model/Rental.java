package com.techisgood.carrentals.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;

import com.techisgood.carrentals.model.audit.VersionedAuditable;
import com.techisgood.carrentals.rentals.RentalStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rentals", catalog = "car_rentals")
@Getter
@Setter
public class Rental extends VersionedAuditable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "char(36) default (uuid())", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rentals_cars"))
    private Car car;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_location_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rental_service_location_id"))
    private ServiceLocation serviceLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clerk_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rentals_clerk"))
    private DbUser clerk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rentals_users"))
    private DbUser renter;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('DATA_ENTRY', 'RENTED', 'RETURNED', 'CANCELED')")
    private RentalStatus status;

    @Column(name = "initial_odometer_reading", precision = 10, scale = 2, nullable = false)
    private BigDecimal initialOdometerReading;

    @Column(name = "ending_odometer_reading", precision = 10, scale = 2)
    private BigDecimal endingOdometerReading;

    @Column(name = "rental_datetime", nullable = false)
    private LocalDateTime rentalDatetime;

    @Column(name = "return_datetime")
    private LocalDateTime returnDatetime;

}
