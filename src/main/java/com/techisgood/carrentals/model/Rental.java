package com.techisgood.carrentals.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rentals", catalog = "car_rentals")
@Getter
@Setter
public class Rental {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "char(36) default (uuid())", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rentals_cars"))
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clerk_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rentals_clerk"))
    private DbUser clerk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rentals_users"))
    private DbUser renter;

    @Column(name = "initial_odometer_reading", precision = 10, scale = 2, nullable = false)
    private BigDecimal initialOdometerReading;

    @Column(name = "ending_odometer_reading", precision = 10, scale = 2)
    private BigDecimal endingOdometerReading;

    @Column(name = "rental_datetime", nullable = false)
    private LocalDateTime rentalDatetime;

    @Column(name = "return_datetime")
    private LocalDateTime returnDatetime;

    @CreatedDate
    @Column(name = "created_at", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", columnDefinition = "char(36)")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by", columnDefinition = "char(36)")
    private String updatedBy;
}
