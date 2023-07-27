package com.techisgood.carrentals.user;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.techisgood.carrentals.model.DbUserDemographics.Gender;

import lombok.Data;
import lombok.NoArgsConstructor;

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
	
}