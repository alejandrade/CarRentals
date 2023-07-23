CREATE TABLE car_rentals.user_demographics (
                                               id CHAR(36) NOT NULL PRIMARY KEY DEFAULT (UUID()),
                                               user_id CHAR(36) NOT NULL,
                                               first_name VARCHAR(100) NULL,
                                               middle_initial CHAR(1) NULL,
                                               last_name VARCHAR(100) NULL,
                                               date_of_birth DATE NULL,
                                               gender ENUM('Male', 'Female', 'Other', 'Prefer Not To Say') NULL,
                                               address VARCHAR(255) NULL,
                                               city VARCHAR(100) NULL,
                                               state VARCHAR(100) NULL,
                                               postal_code VARCHAR(20) NULL,
                                               country VARCHAR(100) NULL,
                                               additional_info TEXT NULL,
                                               FOREIGN KEY (user_id) REFERENCES car_rentals.users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE car_rentals.user_licenses (
                                           id CHAR(36) NOT NULL PRIMARY KEY DEFAULT (UUID()),
                                           user_id CHAR(36) NOT NULL,
                                           license_number VARCHAR(20) NOT NULL,
                                           issuing_state VARCHAR(2) NOT NULL, -- Two-letter state code (e.g., CA, TX, NY)
                                           date_of_issue DATE NOT NULL,
                                           expiration_date DATE NOT NULL,
                                           license_class VARCHAR(10) NULL,    -- Example: Class A, Class B, etc.
                                           FOREIGN KEY (user_id) REFERENCES car_rentals.users(id) ON DELETE CASCADE ON UPDATE CASCADE
);
