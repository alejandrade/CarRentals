ALTER TABLE car_rentals.rentals
    ADD INDEX idx_rental_datetime (rental_datetime),
ADD INDEX idx_return_datetime (return_datetime);

ALTER TABLE car_rentals.cars
    ADD COLUMN rent_price DECIMAL(10,2);

