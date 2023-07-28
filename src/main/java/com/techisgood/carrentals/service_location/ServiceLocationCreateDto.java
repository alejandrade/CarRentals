package com.techisgood.carrentals.service_location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class ServiceLocationCreateDto {

    @NotBlank(message = "name is required")
    @Size(max = 100, message = "name should not exceed 100 characters")
	private String name;
    

    @NotBlank(message = "address is required")
    @Size(max = 255, message = "address should not exceed 255 characters")
	private String address;

    @NotBlank(message = "city is required")
    @Size(max = 100, message = "city should not exceed 100 characters")
	private String city;

    @NotBlank(message = "state is required")
    @Size(max = 100, message = "state should not exceed 100 characters")
	private String state;
    
    @NotBlank(message = "postalCode is required")
    @Size(max = 20, message = "postalCode should not exceed 20 characters")
	private String postalCode;
    
    @NotBlank(message = "country is required")
    @Size(max = 100, message = "country should not exceed 100 characters")
	private String country;
    
    @Size(max = 65535, message = "city should not exceed 65535 characters")
	private String additionalInfo;
}
