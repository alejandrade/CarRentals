alter table car_rentals.rentals
    add paid boolean DEFAULT false;

alter table car_rentals.rentals
    add cleaning_fee boolean DEFAULT false,
    add damaged_fee boolean DEFAULT false;

create index rental_status_idx
    on car_rentals.rentals (status);