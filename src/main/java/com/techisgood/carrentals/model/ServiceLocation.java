package com.techisgood.carrentals.model;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.techisgood.carrentals.model.audit.VersionedAuditable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "service_location", catalog = "car_rentals")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class ServiceLocation extends VersionedAuditable {

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


	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="location_id")
	private List<Car> cars;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="location_id")
	private List<ServiceLocationClerk> clerks;
	
}
