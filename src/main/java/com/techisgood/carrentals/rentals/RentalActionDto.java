package com.techisgood.carrentals.rentals;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RentalActionDto {
    private BigDecimal odometer;
    private Integer version;
}
