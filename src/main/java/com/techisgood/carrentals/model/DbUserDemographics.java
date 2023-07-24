package com.techisgood.carrentals.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users_demographics")
@Getter
@Setter
public class DbUserDemographics {
	
	public enum Gender {
        Male, Female, Other, Prefer_Not_To_Say;
    }
	
	@Id private String id;
	@Column(nullable=false) private String user_id;
	@Column private String first_name;
	@Column private String middle_initial;
	@Column private String last_name;
	@Column @Temporal(TemporalType.DATE) private Date date_of_birth;
	@Column(columnDefinition = "ENUM('Male', 'Female', 'Other', 'Prefer Not To Say')") @Enumerated(EnumType.STRING) private String gender;
	@Column private String address;
	@Column private String city;
	@Column private String state;
	@Column private String postal_code;
	@Column private String country;
	@Column private String additional_info;
	
	
	private String fullNameFormatted;
	
	@PostConstruct
	public void init() {
		fullNameFormatted = "";
		if (first_name != null) {
			fullNameFormatted += first_name;
		}
		if (middle_initial != null) { 
			fullNameFormatted += " " + middle_initial;
		}
		if (last_name != null) {
			fullNameFormatted += " " + last_name;
		}
	}
}
