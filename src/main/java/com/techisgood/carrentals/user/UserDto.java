package com.techisgood.carrentals.user;


import com.techisgood.carrentals.authorities.UserAuthority;
import com.techisgood.carrentals.model.Authority;
import com.techisgood.carrentals.model.DbUser;
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

    private Integer version;

    private List<String> serviceLocationId;

    private List<UserAuthority> authorities;

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
        userDto.setVersion(dbUser.getVersion());
        if (dbUser.getServiceLocationClerks() != null) {
            userDto.setServiceLocationId(dbUser.getServiceLocationClerks().stream()
                    .map(x -> x.getServiceLocation().getId()).toList());
        }

        if (dbUser.getAuthorities() != null) {
            userDto.setAuthorities(dbUser.getAuthorities().stream()
                    .map(Authority::getAuthority)
                    .map(UserAuthority::valueOf)// Assuming Authority has a getName method to get the authority name
                    .collect(Collectors.toList()));
        }

        // Assuming you also have a from method in UserDemographicsDto
        if (dbUser.getUserDemographics() != null) {
            userDto.setUserDemographics(UserDemographicsDto.from(dbUser.getUserDemographics()));
        }

        return userDto;
    }
}
