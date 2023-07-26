package com.techisgood.carrentals.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users", catalog = "car_rentals")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class DbUser {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "char(36) default (uuid())", nullable = false)
    private String id;

    @Column(unique = true, length = 255)
    private String email;

    @Column(name = "phone_number", unique = true, length = 15)
    private String phoneNumber;

    @Column(name = "is_email_auth", nullable = false, columnDefinition = "tinyint(1)")
    private Boolean isEmailAuth;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean enabled = true;

    @Column(name = "account_non_expired", columnDefinition = "tinyint(1) default 1")
    private Boolean accountNonExpired = true;

    @Column(name = "credentials_non_expired", columnDefinition = "tinyint(1) default 1")
    private Boolean credentialsNonExpired = true;

    @Column(name = "account_non_locked", columnDefinition = "tinyint(1) default 1")
    private Boolean accountNonLocked = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Authority> authorities;

    @CreatedDate
    @Column(name = "created_at", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @CreatedBy
    @Column(name = "created_by", columnDefinition = "char(36)")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by", columnDefinition = "char(36)")
    private String updatedBy;
}
