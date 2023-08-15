package com.techisgood.carrentals.rentals;

import com.techisgood.carrentals.model.Car;
import com.techisgood.carrentals.model.DbUser;
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
    private String renterId;
    private RentalStatus status;
    private BigDecimal initialOdometerReading;
    private BigDecimal endingOdometerReading;
    private LocalDateTime rentalDatetime;
    private LocalDateTime returnDatetime;
    private Integer version;
    private Boolean paid;
    private Integer cleaningFee;
    private Integer damagedFee;
    private Integer insuranceFee;

    // Audit properties
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    public static RentalDto from(Rental rental) {
        RentalDto dto = new RentalDto();
        dto.setId(rental.getId());

        Car car = rental.getCar();
        if (car != null) {
            dto.setCarId(car.getId());
        }
        DbUser clerk = rental.getClerk();
        if (clerk != null) {
            dto.setClerkId(clerk.getId());
        }
        DbUser renter = rental.getRenter();
        if (renter != null) {
            dto.setRenterPhoneNumber(renter.getPhoneNumber());
            dto.setRenterId(renter.getId());
        }



        dto.setStatus(rental.getStatus());
        dto.setInitialOdometerReading(rental.getInitialOdometerReading());
        dto.setEndingOdometerReading(rental.getEndingOdometerReading());
        dto.setRentalDatetime(rental.getRentalDatetime());
        dto.setReturnDatetime(rental.getReturnDatetime());
        dto.setVersion(rental.getVersion());
        dto.setCleaningFee(rental.getCleaningFee());
        dto.setDamagedFee(rental.getDamagedFee());
        dto.setInsuranceFee(rental.getInsuranceFee());

        // Setting audit properties
        dto.setCreatedAt(rental.getCreatedAt());
        dto.setUpdatedAt(rental.getUpdatedAt());
        dto.setCreatedBy(rental.getCreatedBy());
        dto.setUpdatedBy(rental.getUpdatedBy());

        return dto;
    }
}
