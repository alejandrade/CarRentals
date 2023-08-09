package com.techisgood.carrentals.rentals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class RentalCreateDto {
	@NotBlank(message="'Car Id' is required")
    private String carId;
	@NotBlank(message="'Phone Number' is required")
    private String renterPhoneNumber;
	@NotNull(message="'Status' must be 'RESERVED, RENTED, RETURNED, CANCELED'")
    private RentalStatus status;
	@NotNull
    private LocalDateTime rentalDatetime;
}
