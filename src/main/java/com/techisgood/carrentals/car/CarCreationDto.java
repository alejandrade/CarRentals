package com.techisgood.carrentals.car;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarCreationDto {

    @NotBlank(message = "Make is required")
    @Size(max = 255, message = "Make should not exceed 255 characters")
    private String make;

    @NotNull(message = "Rent price is required")
    @PositiveOrZero(message = "Rent price must be positive or zero")
    private BigDecimal rentPrice;

    @NotBlank(message = "Model is required")
    @Size(max = 255, message = "Model should not exceed 255 characters")
    private String model;

    @NotNull(message = "Year is required")
    private Integer year;

    @NotBlank(message = "VIN is required")
    @Size(min = 17, max = 17, message = "VIN should be 17 characters long")
    private String vin;

    @NotNull(message = "color is required")
    @NotBlank(message = "Color is required")
    @Size(max = 50, message = "Color should not exceed 50 characters")
    private String color;

    @NotNull(message = "Mileage is required")
    @PositiveOrZero(message = "Mileage must be positive or zero")
    private BigDecimal mileage;

    @PositiveOrZero(message = "Price must be positive or zero")
    private BigDecimal price;

    @NotNull(message = "Availability is required")
    private Boolean availability = true;

    @NotBlank(message = "License Plate is required")
    @Size(max = 50, message = "License Plate should not exceed 50 characters")
    private String licensePlate;

    @NotBlank(message = "Status is required")
    @Size(max = 50, message = "Status should not exceed 50 characters")
    private String status;

    @PositiveOrZero(message = "Version must be positive or zero")
    private Integer version = 0;
}