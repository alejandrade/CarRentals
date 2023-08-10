ALTER TABLE car_rentals.car_service_location_history
    ADD COLUMN version int default 0 after `end_mileage`;