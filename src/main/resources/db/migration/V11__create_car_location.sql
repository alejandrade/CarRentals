CREATE TABLE car_rentals.service_location (
       id CHAR(36) NOT NULL PRIMARY KEY DEFAULT (UUID()),
       name VARCHAR(100) NOT NULL,
       address VARCHAR(255) NULL,
       city VARCHAR(100) NULL,
       state VARCHAR(100) NULL,
       postal_code VARCHAR(20) NULL,
       country VARCHAR(100) NULL,
       additional_info TEXT NULL,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       created_by CHAR(36),
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       updated_by CHAR(36)
);

CREATE TABLE car_rentals.car_service_location (
id CHAR(36) NOT NULL PRIMARY KEY DEFAULT (UUID()),
car_id CHAR(36) NOT NULL,
location_id CHAR(36) NOT NULL,
service_started TIMESTAMP NULL,
service_ended TIMESTAMP NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
created_by CHAR(36),
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
updated_by CHAR(36),
CONSTRAINT fk_car_service_location_cars FOREIGN KEY (car_id) REFERENCES car_rentals.cars(id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_car_service_location_service_location FOREIGN KEY (location_id) REFERENCES car_rentals.service_location(id) ON DELETE CASCADE ON UPDATE CASCADE
);