/*
CREATE TABLE car_rentals.payments_customers (
                                               id CHAR(36) NOT NULL PRIMARY KEY DEFAULT (UUID()),
                                               user_id CHAR(36) NOT NULL,
                                               customer_id VARCHAR(32),
                                               FOREIGN KEY (user_id) REFERENCES car_rentals.users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE car_rentals.payments_invoices (
                                           id CHAR(36) NOT NULL PRIMARY KEY DEFAULT (UUID()),
                                           user_id CHAR(36) NOT NULL,
                                           invoice_id
                                           FOREIGN KEY (user_id) REFERENCES car_rentals.users(id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE car_rentals.payments_payments (
);
*/