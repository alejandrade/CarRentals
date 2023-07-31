package com.techisgood.carrentals.user;


import com.techisgood.carrentals.model.Authority;
import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.model.UserInsurance;
import com.techisgood.carrentals.model.UserLicense;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {

    private String id;

    @NotBlank
    @Email
    @Size(max = 255)
    private String email;

    @NotBlank
    @Size(max = 15)
    private String phoneNumber;

    @NotNull
    private Boolean isEmailAuth;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Boolean accountNonExpired;

    @NotNull
    private Boolean credentialsNonExpired;

    @NotNull
    private Boolean accountNonLocked;

    private List<String> authorities;

    private List<UserLicenseDto> userLicenses;

    private List<UserInsuranceDto> userInsurances;

    private UserDemographicsDto userDemographics;

    public static UserDto from(DbUser dbUser) {
        UserDto userDto = new UserDto();

        userDto.setId(dbUser.getId());
        userDto.setEmail(dbUser.getEmail());
        userDto.setPhoneNumber(dbUser.getPhoneNumber());
        userDto.setIsEmailAuth(dbUser.getIsEmailAuth());
        userDto.setEnabled(dbUser.getEnabled());
        userDto.setAccountNonExpired(dbUser.getAccountNonExpired());
        userDto.setCredentialsNonExpired(dbUser.getCredentialsNonExpired());
        userDto.setAccountNonLocked(dbUser.getAccountNonLocked());

        if (dbUser.getAuthorities() != null) {
            userDto.setAuthorities(dbUser.getAuthorities().stream()
                    .map(Authority::getAuthority)  // Assuming Authority has a getName method to get the authority name
                    .collect(Collectors.toList()));
        }

        // Assuming you also have a from method in UserDemographicsDto
        if (dbUser.getUserDemographics() != null) {
            userDto.setUserDemographics(UserDemographicsDto.from(dbUser.getUserDemographics()));
        }

        if (dbUser.getUserLicenses() != null && !dbUser.getUserLicenses().isEmpty()) {
            userDto.setUserLicenses(dbUser.getUserLicenses().stream().map(UserLicenseDto::from).toList());
        }

        if (dbUser.getUserInsurances() != null && !dbUser.getUserInsurances().isEmpty()) {
            userDto.setUserInsurances(dbUser.getUserInsurances().stream().map(UserInsuranceDto::from).toList());
        }

        return userDto;
    }
}
