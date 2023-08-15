alter table car_rentals.payments_invoice
add column cleaning_fee int default 0,
add column damage_fee int default 0,
add column other_fee int default 0
;