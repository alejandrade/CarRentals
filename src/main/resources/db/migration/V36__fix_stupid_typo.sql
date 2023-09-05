alter table car_rentals.service_location_clerk
    drop foreign key service_location_clerk_location_id;

drop index service_location_clerk_key on car_rentals.service_location_clerk;


alter table car_rentals.service_location_clerk
    drop key service_location_clerk_key;

alter table car_rentals.service_location_clerk
    add constraint service_location_clerk_key
        unique (location_id, user_id);

alter table car_rentals.service_location_clerk
    add constraint service_location_clerk_service_location_id_fk
        foreign key (location_id) references service_location (id);


