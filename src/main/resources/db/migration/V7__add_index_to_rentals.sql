ALTER TABLE car_rentals.rentals
    ADD COLUMN clerk_id CHAR(36) NOT NULL,
    ADD INDEX idx_rental_datetime (rental_datetime),
    ADD INDEX idx_return_datetime (return_datetime),
    ADD CONSTRAINT fk_rentals_clerk FOREIGN KEY(clerk_id) REFERENCES car_rentals.users(id);

ALTER TABLE car_rentals.cars
    ADD COLUMN rent_price DECIMAL(10,2);

ALTER TABLE car_rentals.rental_pictures
    ADD COLUMN taken_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;