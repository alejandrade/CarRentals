package com.techisgood.carrentals.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Embeddable
public class ServiceLocationCarKey implements Serializable {

    @Column(name = "location_id", columnDefinition = "char(36)", nullable = false, length = 36)
    private String locationId;

    @Column(name = "car_id", columnDefinition = "char(36)", nullable = false, length = 36)
    private String carId;

    // Getters, setters, equals(), and hashCode() methods
}