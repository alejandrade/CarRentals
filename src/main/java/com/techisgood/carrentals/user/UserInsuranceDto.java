package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.UserInsurance;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserInsuranceDto {

    private String id;

    private String userId;

    @NotNull(message = "policyNumber is required")
    private String policyNumber;

    @NotNull(message = "provider is required")
    private String provider;

    private String frontCardPictureKey;

    private String backCardPictureKey;

    @NotNull(message = "endDate is required")
    private LocalDateTime endDate;

    private boolean active;

    public static UserInsuranceDto from(UserInsurance userInsurance) {
        UserInsuranceDto userInsuranceDto = new UserInsuranceDto();
        userInsuranceDto.setId(userInsurance.getId());
        userInsuranceDto.setUserId(userInsurance.getUser().getId());
        userInsuranceDto.setPolicyNumber(userInsurance.getPolicyNumber());
        userInsuranceDto.setProvider(userInsurance.getProvider());
        userInsuranceDto.setFrontCardPictureKey(userInsurance.getFrontCardPictureKey());
        userInsuranceDto.setBackCardPictureKey(userInsurance.getBackCardPictureKey());
        userInsuranceDto.setEndDate(userInsurance.getEndDate());
        userInsuranceDto.setActive(userInsurance.isActive());

        return userInsuranceDto;
    }

    // Additional methods and constructors if needed
}
