alter table car_rentals.rentals add column service_location_id char (36),
add constraint fk_rental_service_location_id foreign key (service_location_id) references car_rentals.service_location(id)