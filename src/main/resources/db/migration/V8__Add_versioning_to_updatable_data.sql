ALTER TABLE car_rentals.cars
    ADD COLUMN version int default 0;

ALTER TABLE car_rentals.rentals
    ADD COLUMN version int default 0;

ALTER TABLE car_rentals.user_demographics
    ADD COLUMN version int default 0;

ALTER TABLE car_rentals.user_licenses
    ADD COLUMN version int default 0;