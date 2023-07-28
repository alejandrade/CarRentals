package com.techisgood.carrentals.model;

import com.techisgood.carrentals.model.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "rental_pictures", catalog = "car_rentals")
@Getter
@Setter
public class RentalPicture extends Auditable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "char(36) default (uuid())", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pictures_rentals"))
    private Rental rental;

    @Column(length = 50)
    private String angle;

    @Column(name = "s3_url", length = 500, nullable = false)
    private String s3Url;

    @Column(name = "is_initial_picture", nullable = false, columnDefinition = "tinyint(1)")
    private Boolean isInitialPicture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taken_by", nullable = false, foreignKey = @ForeignKey(name = "fk_pictures_users"))
    private DbUser takenBy;

    @Column(name = "taken_at", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime takenAt;
}
