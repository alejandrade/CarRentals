alter table car_rentals.payments_invoice
    drop column day_price;

alter table car_rentals.payments_invoice
    drop column days;

alter table car_rentals.payments_invoice
    drop column cleaning_fee;

alter table car_rentals.payments_invoice
    drop column damage_fee;

alter table car_rentals.payments_invoice
    drop column other_fee;


alter table car_rentals.payments_invoice
    add note varchar(255) null;

alter table car_rentals.rentals
    modify cleaning_fee integer default 0 null;

alter table car_rentals.rentals
    modify damaged_fee integer default 0 null;

alter table car_rentals.rentals
    add insurance_fee int null;
