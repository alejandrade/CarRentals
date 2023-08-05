package com.techisgood.carrentals.model;

import com.techisgood.carrentals.model.audit.VersionedAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "user_licenses", catalog = "car_rentals")
@Getter
@Setter
@EntityListeners({AuditingEntityListener.class})
public class UserLicense extends VersionedAuditable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "char(36) default (uuid())", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private DbUser user;

    @Column(name = "license_number", length = 20, nullable = false)
    private String licenseNumber;

    @Column(name = "issuing_state", length = 2, nullable = false)
    private String issuingState;

    @Column(name = "date_of_issue", nullable = false)
    private java.sql.Date dateOfIssue;

    @Column(name = "expiration_date", nullable = false)
    private java.sql.Date expirationDate;

    @Column(name = "license_class", length = 10)
    private String licenseClass;

    @Column(name = "back_card_picture_key")
    private String backCardPictureKey;

    @Column(name = "front_card_picture_key")
    private String frontCardPictureKey;

    @Column(name = "active")
    private boolean active;

}
