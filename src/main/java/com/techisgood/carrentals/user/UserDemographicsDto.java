package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.DbUserDemographics;
import com.techisgood.carrentals.model.DbUserDemographics.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserDemographicsDto {

	@NotNull(message = "userId is required")
	private String userId;

	@NotNull(message = "firstName is required")
	private String firstName;
	
	private String middleInitial;
	private String lastName;
	private LocalDate dateOfBirth;
	private Gender gender;
	private String address;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private String additionalInfo;

	public static UserDemographicsDto from(DbUserDemographics dbUserDemographics) {
		UserDemographicsDto userDemographicsDto = new UserDemographicsDto();

		userDemographicsDto.setFirstName(dbUserDemographics.getFirstName());
		userDemographicsDto.setMiddleInitial(dbUserDemographics.getMiddleInitial());
		userDemographicsDto.setLastName(dbUserDemographics.getLastName());
		userDemographicsDto.setDateOfBirth(dbUserDemographics.getDateOfBirth());
		userDemographicsDto.setGender(dbUserDemographics.getGender());
		userDemographicsDto.setAddress(dbUserDemographics.getAddress());
		userDemographicsDto.setCity(dbUserDemographics.getCity());
		userDemographicsDto.setState(dbUserDemographics.getState());
		userDemographicsDto.setPostalCode(dbUserDemographics.getPostalCode());
		userDemographicsDto.setCountry(dbUserDemographics.getCountry());
		userDemographicsDto.setAdditionalInfo(dbUserDemographics.getAdditionalInfo());

		return userDemographicsDto;
	}
	
}