package com.techisgood.carrentals.service_location;

import java.time.LocalDateTime;

import com.techisgood.carrentals.model.ServiceLocation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

	public static ServiceLocationDto from(ServiceLocation entity) {
		if (entity == null) {
			return null;
		}

		ServiceLocationDto dto = new ServiceLocationDto();

		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setAddress(entity.getAddress());
		dto.setCity(entity.getCity());
		dto.setState(entity.getState());
		dto.setPostalCode(entity.getPostalCode());
		dto.setCountry(entity.getCountry());
		dto.setAdditionalInfo(entity.getAdditionalInfo());

		// Assuming the VersionedAuditable class has getters for createdAt, updatedAt, etc.
		dto.setCreatedAt(entity.getCreatedAt());
		dto.setUpdatedAt(entity.getUpdatedAt());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setUpdatedBy(entity.getUpdatedBy());
		dto.setVersion(entity.getVersion());

		return dto;
	}
}
