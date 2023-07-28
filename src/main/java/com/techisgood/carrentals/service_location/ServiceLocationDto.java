package com.techisgood.carrentals.service_location;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ServiceLocationDto {
	private String id;
	private String name;
	private String address;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private String additionalInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Integer version = 0;
}
