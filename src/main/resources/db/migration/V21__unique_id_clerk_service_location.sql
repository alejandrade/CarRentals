ALTER TABLE car_rentals.service_location
    add constraint service_location_name_uidx
        unique (name);


ALTER TABLE car_rentals.users
    ADD COLUMN version int default 0;
