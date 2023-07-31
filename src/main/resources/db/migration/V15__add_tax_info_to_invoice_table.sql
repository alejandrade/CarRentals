alter table car_rentals.payments_invoice
add column sub_total int not null after `days`,
add column tax_rate decimal(5,4) not null after `sub_total`,
add column tax_total int not null after `tax_rate`
;