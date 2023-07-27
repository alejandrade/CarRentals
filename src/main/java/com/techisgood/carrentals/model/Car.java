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
@Table(name = "cars", catalog = "car_rentals")
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "char(36) default (uuid())", nullable = false)
    private String id;

    @Column(nullable = false, length = 255)
    private String make;

    @Column(name = "rent_price", precision = 10, scale = 2)
    private BigDecimal rentPrice;

    @Column(nullable = false, length = 255)
    private String model;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false, unique = true, length = 17)
    private String vin;

    @Column(nullable = false, length = 50)
    private String color;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal mileage;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean availability = true;

    @Column(name = "license_plate", nullable = false, length = 50)
    private String licensePlate;

    @Column(nullable = false, length = 50)
    private String status;

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
