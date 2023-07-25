ALTER TABLE car_rentals.users
    ADD COLUMN created_by CHAR(36),
    ADD COLUMN updated_by CHAR(36);

ALTER TABLE car_rentals.authorities
    ADD COLUMN created_by CHAR(36),
    ADD COLUMN updated_by CHAR(36);

ALTER TABLE car_rentals.user_demographics
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN created_by CHAR(36),
    ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ADD COLUMN deleted_at TIMESTAMP NULL,
    ADD COLUMN updated_by CHAR(36);

ALTER TABLE car_rentals.user_licenses
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN created_by CHAR(36),
    ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ADD COLUMN deleted_at TIMESTAMP NULL,
    ADD COLUMN updated_by CHAR(36);
