package com.techisgood.carrentals.model;

import com.techisgood.carrentals.model.audit.Auditable;
import com.techisgood.carrentals.user.UserEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "users", catalog = "car_rentals")
@Getter
@Setter
@EntityListeners({AuditingEntityListener.class, UserEntityListener.class})
public class DbUser extends Auditable {

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
    private Boolean isEmailAuth = false;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean enabled = true;

    @Column(name = "account_non_expired", columnDefinition = "tinyint(1) default 1")
    private Boolean accountNonExpired = true;

    @Column(name = "credentials_non_expired", columnDefinition = "tinyint(1) default 1")
    private Boolean credentialsNonExpired = true;

    @Column(name = "account_non_locked", columnDefinition = "tinyint(1) default 1")
    private Boolean accountNonLocked = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Authority> authorities;
}
