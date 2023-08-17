alter table car_rentals.payments_invoice
    add invoice_type varchar(50) null;


create unique index payments_invoice_pk
    ON car_rentals.payments_invoice(invoice_type, rental_id, payer_id);

