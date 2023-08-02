package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.UserLicense;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.sql.Date;

@Data
@NoArgsConstructor
public class UserLicenseDto {

    private String id;

    private String userId;

    @NotNull(message = "licenseNumber is required")
    private String licenseNumber;

    @NotNull(message = "issuingState is required")
    private String issuingState;

    @NotNull(message = "dateOfIssue is required")
    private Date dateOfIssue;

    @NotNull(message = "expirationDate is required")
    private Date expirationDate;

    private String licenseClass;

    private String backCardPicture;

    private String frontCardPicture;

    private boolean active;

    public static UserLicenseDto from(UserLicense userLicense) {
        UserLicenseDto userLicenseDto = new UserLicenseDto();
        userLicenseDto.setId(userLicense.getId());
        userLicenseDto.setUserId(userLicense.getUser().getId());
        userLicenseDto.setLicenseNumber(userLicense.getLicenseNumber());
        userLicenseDto.setIssuingState(userLicense.getIssuingState());
        userLicenseDto.setDateOfIssue(userLicense.getDateOfIssue());
        userLicenseDto.setExpirationDate(userLicense.getExpirationDate());
        userLicenseDto.setLicenseClass(userLicense.getLicenseClass());
        userLicenseDto.setBackCardPicture(userLicense.getBackCardPicture());
        userLicenseDto.setFrontCardPicture(userLicense.getFrontCardPicture());
        userLicenseDto.setActive(userLicense.isActive());

        return userLicenseDto;
    }

    // Additional methods and constructors if needed
}
