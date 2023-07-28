package com.techisgood.carrentals.rentals;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateRentalPictureDto {
    private String rentalId;          // Use the ID instead of the entire Rental object
    private RentalPictureAngle angle;
    private Boolean isInitialPicture;
    private String takenById;         // Use the ID instead of the entire DbUser object
    private LocalDateTime takenAt;

}
