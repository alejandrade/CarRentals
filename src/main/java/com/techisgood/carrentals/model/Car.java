package com.techisgood.carrentals.model;

import java.math.BigDecimal;
import java.util.List;

import com.techisgood.carrentals.car.CatStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.techisgood.carrentals.model.audit.VersionedAuditable;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cars", catalog = "car_rentals")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Car extends VersionedAuditable {

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
    @Enumerated(EnumType.STRING)
    private CatStatus status;

    @Column(name = "short_id", length = 8, insertable = false, updatable = false) // Added generated column mapping
    private String shortId;
    
    @OneToMany
    @JoinColumn(name="car_id", referencedColumnName = "id")
    private List<CarServiceLocationHistory> locationsHistory;

    @OneToOne
    @JoinColumn(name="location_id", referencedColumnName = "id")
    private ServiceLocation serviceLocation;
}
