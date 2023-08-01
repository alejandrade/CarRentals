create table car_rentals.service_location_car(
location_id char(36) not null,
car_id char(36) not null,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
constraint fk_service_location_car_location_id foreign key (location_id) references car_rentals.service_location(id),
constraint fk_service_location_car_car_id foreign key (car_id) references car_rentals.cars(id),
constraint fk_service_location_car_uq unique(location_id,car_id)
);
