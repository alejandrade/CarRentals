CREATE TABLE car_rentals.cars
(
    id            CHAR(36)           NOT NULL PRIMARY KEY DEFAULT (UUID()),
    make          VARCHAR(255)       NOT NULL,
    model         VARCHAR(255)       NOT NULL,
    year          INT                NOT NULL,                          -- Year should be in a valid range
    vin           VARCHAR(17) UNIQUE NOT NULL,                          -- VINs are typically 17 characters long
    color         VARCHAR(50)        NOT NULL,
    mileage       DECIMAL(10, 2)     NOT NULL             DEFAULT 0.00, -- can use DECIMAL to have precision for kilometers or miles
    price         DECIMAL(10, 2),
    availability  BOOLEAN            NOT NULL             DEFAULT TRUE,
    license_plate VARCHAR(50)        NOT NULL,
    status        VARCHAR(50)        NOT NULL,

    -- Adding the provided columns:
    created_at    TIMESTAMP                               DEFAULT CURRENT_TIMESTAMP,
    created_by    CHAR(36),
    updated_at    TIMESTAMP                               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by    CHAR(36)
);


-- Modified rentals table
CREATE TABLE car_rentals.rentals (
                                     id CHAR(36) NOT NULL PRIMARY KEY DEFAULT (UUID()),
                                     car_id CHAR(36) NOT NULL,
                                     renter_id CHAR(36) NOT NULL,
                                     initial_odometer_reading DECIMAL(10,2) NOT NULL,
                                     ending_odometer_reading DECIMAL(10,2),
                                     rental_datetime TIMESTAMP NOT NULL,
                                     return_datetime TIMESTAMP,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     created_by CHAR(36),
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     updated_by CHAR(36),

                                     CONSTRAINT fk_rentals_cars FOREIGN KEY(car_id) REFERENCES car_rentals.cars(id),
                                     CONSTRAINT fk_rentals_users FOREIGN KEY(renter_id) REFERENCES car_rentals.users(id)
);

-- Rental pictures table
CREATE TABLE car_rentals.rental_pictures (
                                             id CHAR(36) NOT NULL PRIMARY KEY DEFAULT (UUID()),
                                             rental_id CHAR(36) NOT NULL,
                                             angle VARCHAR(50),
                                             s3_url VARCHAR(500) NOT NULL,
                                             is_initial_picture BOOLEAN NOT NULL,
                                             taken_by CHAR(36) NOT NULL, -- Who took the picture

                                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                             created_by CHAR(36),
                                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                             updated_by CHAR(36),

                                             CONSTRAINT fk_pictures_rentals FOREIGN KEY(rental_id) REFERENCES car_rentals.rentals(id),
                                             CONSTRAINT fk_pictures_users FOREIGN KEY(taken_by) REFERENCES car_rentals.users(id)

);


