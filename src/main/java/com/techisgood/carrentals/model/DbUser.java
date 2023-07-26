package com.techisgood.carrentals.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class DbUser {

    @Id
    @Column(columnDefinition = "char(36) default (uuid())", nullable = false)
    private String id;

    @Column(unique = true, length = 255)
    private String email;

    @Column(name = "phone_number", unique = true, length = 15)
    private String phoneNumber;

    @Column(name = "is_email_auth", nullable = false, columnDefinition = "tinyint(1)")
    private Boolean isEmailAuth;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean enabled;

    @Column(name = "accountNonExpired", columnDefinition = "tinyint(1) default 1")
    private Boolean accountNonExpired;

    @Column(name = "credentialsNonExpired", columnDefinition = "tinyint(1) default 1")
    private Boolean credentialsNonExpired;

    @Column(name = "accountNonLocked", columnDefinition = "tinyint(1) default 1")
    private Boolean accountNonLocked;

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
