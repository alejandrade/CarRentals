

alter table car_rentals.service_location_clerk
add column user_id char(36) not null,
add column first_name varchar(100),
add column last_name varchar(100),
add column status varchar(36),
add column created_by char(36) not null,
add column created_at timestamp not null default current_timestamp,
add column updated_by char(36),
add column updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
add constraint service_location_clerk_user_id foreign key (user_id) references car_rentals.users(id),
add constraint service_location_clerk_location_id foreign key (location_id) references car_rentals.service_location(id)
;