alter table car_rentals.payments_invoice
drop constraint fk_payments_invoice_users_paid,
drop column paid_by,
add column paid_by_id char(36) null after `total`,
add constraint fk_payments_invoice_paid_by_id foreign key (paid_by_id) references car_rentals.users(id)
;