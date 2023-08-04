package com.techisgood.carrentals.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_location_car", catalog = "car_rentals")
@Getter
@Setter
public class ServiceLocationCar {

    @EmbeddedId
    private ServiceLocationCarKey id = new ServiceLocationCarKey();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("locationId")
    @JoinColumn(name = "location_id", columnDefinition = "char(36) default (uuid())", referencedColumnName = "id", insertable = false, updatable = false)
    private ServiceLocation serviceLocation;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("carId")
    @JoinColumn(name = "car_id", columnDefinition = "char(36) default (uuid())", referencedColumnName = "id", insertable = false, updatable = false)
    private Car car;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public void setCar(Car car) {
        this.car = car;
        if(car != null) {
            this.id.setCarId(car.getId());
        }
    }

    public void setServiceLocation(ServiceLocation serviceLocation) {
        this.serviceLocation = serviceLocation;
        if(serviceLocation != null) {
            this.id.setLocationId(serviceLocation.getId());
        }
    }

}