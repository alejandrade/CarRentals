package com.techisgood.carrentals.model;

import com.techisgood.carrentals.model.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "authorities", catalog = "car_rentals")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Authority extends Auditable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "char(36) default (uuid())")
    private String id;

    @Column(name = "user_id", nullable = false, columnDefinition = "char(36)")
    private String userId;

    @Column(name = "authority", nullable = false, length = 50)
    private String authority;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private DbUser user;
}
