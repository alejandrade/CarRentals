package com.techisgood.carrentals.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.techisgood.carrentals.model.audit.VersionedAuditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "service_location_clerk", catalog="car_rentals")
@EntityListeners(AuditingEntityListener.class)
public class ServiceLocationClerk extends VersionedAuditable {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "char(36) default (uuid())", nullable = false)
	private String id;

	
    @OneToOne
    @JoinColumn(name = "user_id", columnDefinition = "char(36) default (uuid())", referencedColumnName = "id")
    private DbUser user;

    @OneToOne
    @JoinColumn(name = "location_id", columnDefinition = "char(36) default (uuid())", referencedColumnName = "id")
    private ServiceLocation serviceLocation;

    @Column(name="status")
    String status;
}
