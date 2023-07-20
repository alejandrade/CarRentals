package com.techisgood.carrentals.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class DbUser {

    @Id
    private String id;

    @Column(unique = true)
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "is_email_auth", nullable = false)
    private Boolean isEmailAuth;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(name = "account_non_expired", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean accountNonExpired;

    @Column(name = "credentials_non_expired", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean credentialsNonExpired;

    @Column(name = "account_non_locked", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean accountNonLocked;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
