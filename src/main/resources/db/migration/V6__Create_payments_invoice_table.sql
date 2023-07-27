CREATE TABLE car_rentals.payments_invoices (id CHAR(36) NOT NULL PRIMARY KEY DEFAULT (UUID()),
					rental_id char(36) not null,
					payer_id char(36) not null,
					day_price int(9) not null,
					days int(5) not null,
					total int(12) not null,
					paid_by char(36) default null,
					external_payment_id char(36) default null,
					external_payment_status char(36) default null,
					created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    				created_by CHAR(36),
    				updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    				updated_by char(36),
					CONSTRAINT fk_payments_invoice_rentals foreign key (rental_id) references car_rentals.rentals(id) on delete cascade, on update cascade
					CONSTRAINT fk_payments_invoice_users_payer foreign key (payer_id) references car_rentals.users(id) on delete cascade, on update cascade
					CONSTRAINT fk_payments_invoice_users_paid foreign key (paid_by) references car_rentals.users(id) on delete cascade, on update cascade
);
