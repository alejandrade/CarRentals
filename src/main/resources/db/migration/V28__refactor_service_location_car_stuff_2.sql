create table car_service_location_history(
id CHAR(36) NOT NULL PRIMARY KEY DEFAULT (UUID()),
car_id char(36) not null,
location_id char(36) not null,
start_date TIMESTAMP NULL,
end_date TIMESTAMP NULL,
start_mileage decimal(10,2) null,
end_mileage decimal(10,2) null,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
created_by CHAR(36),
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
updated_by CHAR(36),
CONSTRAINT fk_car_service_location_history_cars FOREIGN KEY (car_id) REFERENCES car_rentals.cars(id),
CONSTRAINT fk_car_service_location_history_service_location FOREIGN KEY (location_id) REFERENCES car_rentals.service_location(id)
);

alter table car_rentals.cars
add column location_id char(36) after status,
add constraint fk_cars_service_location foreign key (location_id) references car_rentals.service_location(id)
;