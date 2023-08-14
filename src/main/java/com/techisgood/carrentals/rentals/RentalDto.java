package com.techisgood.carrentals.rentals;

import com.techisgood.carrentals.model.Rental;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RentalDto {

    private String id;
    private String carId;  // Assuming you want to represent the car with its id
    private String clerkId; // Assuming you want to represent the clerk with its id
    private String renterPhoneNumber; // Assuming you want to represent the renter with its id
    private RentalStatus status;
    private BigDecimal initialOdometerReading;
    private BigDecimal endingOdometerReading;
    private LocalDateTime rentalDatetime;
    private LocalDateTime returnDatetime;
    private Integer version;
    private Boolean paid;
    private Boolean cleaningFee;
    private Boolean damagedFee;

    // Audit properties
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    public static RentalDto from(Rental rental) {
        RentalDto dto = new RentalDto();
        dto.setId(rental.getId());

        if (rental.getCar() != null) {
            dto.setCarId(rental.getCar().getId());
        }
        if (rental.getClerk() != null) {
            dto.setClerkId(rental.getClerk().getId());
        }
        if (rental.getRenter() != null) {
            dto.setRenterPhoneNumber(rental.getRenter().getPhoneNumber());
        }


        dto.setStatus(rental.getStatus());
        dto.setInitialOdometerReading(rental.getInitialOdometerReading());
        dto.setEndingOdometerReading(rental.getEndingOdometerReading());
        dto.setRentalDatetime(rental.getRentalDatetime());
        dto.setReturnDatetime(rental.getReturnDatetime());
        dto.setVersion(rental.getVersion());
        dto.setCleaningFee(rental.getCleaningFee());
        dto.setDamagedFee(rental.getDamagedFee());

        // Setting audit properties
        dto.setCreatedAt(rental.getCreatedAt());
        dto.setUpdatedAt(rental.getUpdatedAt());
        dto.setCreatedBy(rental.getCreatedBy());
        dto.setUpdatedBy(rental.getUpdatedBy());

        return dto;
    }
}
