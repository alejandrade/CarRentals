package com.techisgood.carrentals.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ServiceLocation {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "char(36) default (uuid())")
	private String id;

	@Column(name = "name", length = 100)
	private String name;

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
	
}
