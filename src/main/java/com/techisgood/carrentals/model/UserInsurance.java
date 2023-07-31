package com.techisgood.carrentals.model;

import com.techisgood.carrentals.model.audit.VersionedAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_insurance", catalog = "car_rentals")
@Getter
@Setter
@EntityListeners({AuditingEntityListener.class})
public class UserInsurance extends VersionedAuditable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "char(36) default (uuid())", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private DbUser user;

    @Column(name = "policy_number", length = 50, nullable = false)
    private String policyNumber;

    @Column(name = "provider", length = 100, nullable = false)
    private String provider;

    @Column(name = "front_card_picture")
    private String frontCardPicture;

    @Column(name = "back_card_picture")
    private String backCardPicture;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "active", nullable = false)
    private boolean active = true;


}
