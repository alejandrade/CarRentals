package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.DbUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ClientUserDto {

    private String id;

    private String email;

    private String phoneNumber;

    private Boolean enabled;

    private List<UserLicenseDto> userLicenses;

    private List<UserInsuranceDto> userInsurances;

    private UserDemographicsDto userDemographics;

    public static ClientUserDto from(DbUser dbUser) {
        ClientUserDto userDto = new ClientUserDto();

        userDto.setId(dbUser.getId());
        userDto.setEmail(dbUser.getEmail());
        userDto.setPhoneNumber(dbUser.getPhoneNumber());
        userDto.setEnabled(dbUser.getEnabled());

        // Assuming you also have a from method in UserDemographicsDto
        if (dbUser.getUserDemographics() != null) {
            userDto.setUserDemographics(UserDemographicsDto.from(dbUser.getUserDemographics()));
        }

        return userDto;
    }
}
