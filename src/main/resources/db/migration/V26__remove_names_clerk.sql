alter table car_rentals.service_location_clerk
    drop column first_name;

alter table car_rentals.service_location_clerk
    drop column last_name;

alter table car_rentals.rentals
    modify initial_odometer_reading decimal(10, 2) null;

