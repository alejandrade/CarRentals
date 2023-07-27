package com.techisgood.carrentals.car;

import com.techisgood.carrentals.model.Car;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CarDto {
    private String id;
    private String make;
    private BigDecimal rentPrice;
    private String model;
    private Integer year;
    private String vin;
    private String color;
    private BigDecimal mileage;
    private BigDecimal price;
    private Boolean availability;
    private String licensePlate;
    private String status;

    // Audit properties
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    public static CarDto from(Car car) {
        CarDto dto = new CarDto();
        dto.setId(car.getId());
        dto.setMake(car.getMake());
        dto.setModel(car.getModel());
        dto.setYear(car.getYear());
        dto.setVin(car.getVin());
        dto.setColor(car.getColor());
        dto.setMileage(car.getMileage());
        dto.setPrice(car.getPrice());
        dto.setAvailability(car.getAvailability());
        dto.setLicensePlate(car.getLicensePlate());
        dto.setStatus(car.getStatus());

        // Setting audit properties
        dto.setCreatedAt(car.getCreatedAt());
        dto.setUpdatedAt(car.getUpdatedAt());
        dto.setCreatedBy(car.getCreatedBy());
        dto.setUpdatedBy(car.getUpdatedBy());

        return dto;
    }
}
