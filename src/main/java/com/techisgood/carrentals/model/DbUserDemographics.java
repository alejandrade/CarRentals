package com.techisgood.carrentals.model;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_demographics", catalog = "car_rentals")
@Getter
@Setter
public class DbUserDemographics {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "char(36) default (uuid())")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_DEMOGRAPHICS_USER"))
	private DbUser user;

	@Column(name = "first_name", length = 100)
	private String firstName;

	@Column(name = "middle_initial", length = 1, columnDefinition = "CHAR(1)")
	private String middleInitial;

	@Column(name = "last_name", length = 100)
	private String lastName;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender", columnDefinition = "ENUM('Male', 'Female', 'Other', 'Prefer Not To Say')")
	private Gender gender;

	@Column(name = "address", length = 255)
	private String address;

	@Column(name = "city", length = 100)
	private String city;

	@Column(name = "state", length = 100)
	private String state;

	@Column(name = "postal_code", length = 20)
	private String postalCode;

	@Column(name = "country", length = 100)
	private String country;

	@Column(name = "additional_info", columnDefinition = "TEXT")
	private String additionalInfo;
	public enum Gender {
        Male, Female, Other, Prefer_Not_To_Say;
    }

	public String getFullNameFormatted() {
		String fullNameFormatted = "";
		if (this.firstName != null) {
			fullNameFormatted += this.firstName;
		}
		if (this.middleInitial != null) {
			fullNameFormatted += " " + this.middleInitial;
		}
		if (this.lastName != null) {
			fullNameFormatted += " " + this.lastName;
		}
		return fullNameFormatted;
	}
}
