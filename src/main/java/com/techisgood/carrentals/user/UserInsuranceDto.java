package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.UserInsurance;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserInsuranceDto {

    @NotNull(message = "userId is required")
    private String userId;

    @NotNull(message = "policyNumber is required")
    private String policyNumber;

    @NotNull(message = "provider is required")
    private String provider;

    private String frontCardPicture;

    private String backCardPicture;

    @NotNull(message = "endDate is required")
    private LocalDateTime endDate;

    private boolean active;

    public static UserInsuranceDto from(UserInsurance userInsurance) {
        UserInsuranceDto userInsuranceDto = new UserInsuranceDto();

        userInsuranceDto.setUserId(userInsurance.getUser().getId());
        userInsuranceDto.setPolicyNumber(userInsurance.getPolicyNumber());
        userInsuranceDto.setProvider(userInsurance.getProvider());
        userInsuranceDto.setFrontCardPicture(userInsurance.getFrontCardPicture());
        userInsuranceDto.setBackCardPicture(userInsurance.getBackCardPicture());
        userInsuranceDto.setEndDate(userInsurance.getEndDate());
        userInsuranceDto.setActive(userInsurance.isActive());

        return userInsuranceDto;
    }

    // Additional methods and constructors if needed
}
