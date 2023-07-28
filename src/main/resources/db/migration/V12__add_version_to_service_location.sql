ALTER TABLE car_rentals.service_location
    ADD COLUMN version int default 0;
    
ALTER TABLE car_rentals.car_service_location
    ADD COLUMN version int default 0;