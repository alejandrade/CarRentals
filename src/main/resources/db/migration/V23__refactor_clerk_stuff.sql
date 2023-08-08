alter table car_rentals.service_location_clerk
drop column location_id,
drop column clerk_id,
add column user_id char(36) not null,
add column location_id char(36) not null,
add column first_name varchar(100),
add column last_name varchar(100),
add column status varchar(36),
add column created_by char(36) not null,
add column created_at timestamp not null default current_timestamp,
add column updated_by char(36),
add column updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
;