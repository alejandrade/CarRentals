
CREATE TABLE car_rentals.payments_customer (
                                               id CHAR(36) NOT NULL PRIMARY KEY DEFAULT (UUID()),
                                               user_id CHAR(36) NOT NULL,
                                               customer_id VARCHAR(32),
                                               FOREIGN KEY (user_id) REFERENCES car_rentals.users(id) ON DELETE CASCADE ON UPDATE CASCADE
);