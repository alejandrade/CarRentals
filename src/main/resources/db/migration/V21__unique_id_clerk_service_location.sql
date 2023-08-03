ALTER TABLE car_rentals.service_location_clerk
ADD id char(36) NOT NULL,
ADD PRIMARY KEY (id);

UPDATE car_rentals.service_location_clerk SET id = UUID();

ALTER TABLE car_rentals.service_location
    add constraint service_location_name_uidx
        unique (name);


ALTER TABLE car_rentals.users
    ADD COLUMN version int default 0;
