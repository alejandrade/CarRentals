package com.techisgood.carrentals.rentals;

import com.techisgood.carrentals.model.RentalPicture;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentalPictureDto {

    private String id;
    private String rentalId;          // Use the ID instead of the entire Rental object
    private RentalPictureAngle angle;
    private String s3Url;
    private Boolean isInitialPicture;
    private String takenById;         // Use the ID instead of the entire DbUser object
    private LocalDateTime takenAt;

    // Audit properties
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    public static RentalPictureDto from(RentalPicture rentalPicture) {
        RentalPictureDto dto = new RentalPictureDto();
        dto.setId(rentalPicture.getId());
        dto.setRentalId(rentalPicture.getRental().getId());
        dto.setAngle(rentalPicture.getAngle());
        dto.setS3Url(rentalPicture.getS3Url());
        dto.setIsInitialPicture(rentalPicture.getIsInitialPicture());
        dto.setTakenById(rentalPicture.getTakenBy().getId());
        dto.setTakenAt(rentalPicture.getTakenAt());

        // Setting audit properties
        dto.setCreatedAt(rentalPicture.getCreatedAt());
        dto.setUpdatedAt(rentalPicture.getUpdatedAt());
        dto.setCreatedBy(rentalPicture.getCreatedBy());
        dto.setUpdatedBy(rentalPicture.getUpdatedBy());

        return dto;
    }
}
