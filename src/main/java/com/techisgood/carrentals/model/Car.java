package com.techisgood.carrentals.model;

import com.techisgood.carrentals.model.audit.Auditable;
import com.techisgood.carrentals.model.audit.Versioned;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

@Entity
@Table(name = "cars", catalog = "car_rentals")
@Getter
@Setter
public class Car extends Versioned {

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

    @Embedded
    private Auditable auditable;
}
