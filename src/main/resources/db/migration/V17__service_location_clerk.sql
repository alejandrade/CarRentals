create table car_rentals.service_location_clerk(
location_id char(36) not null,
clerk_id char(36) not null,
started timestamp not null,
ended timestamp null,
constraint service_location_clerk_key unique(location_id,clerk_id),
constraint service_location_clerk_service_location_id foreign key (location_id) references car_rentals.service_location(id),
constraint service_location_clerk_clerk_id foreign key (clerk_id) references car_rentals.users(id)
)
;