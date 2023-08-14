package com.techisgood.carrentals.rentals;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class RentalActionDto {
    private BigDecimal initialOdometerReading;
    private BigDecimal endingOdometerReading;
    private LocalDateTime returnDatetime;
    private Integer version;
    private Boolean cleaningFee;
    private Boolean damagedFee;
}
