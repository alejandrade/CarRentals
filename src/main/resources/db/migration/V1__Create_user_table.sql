CREATE TABLE car_rentals.users (
                                   id CHAR(36) NOT NULL PRIMARY KEY DEFAULT (UUID()),
                                   email VARCHAR(255) UNIQUE,
                                   phone_number VARCHAR(15) UNIQUE,
                                   is_email_auth BOOLEAN NOT NULL,
                                   enabled BOOLEAN NOT NULL DEFAULT TRUE,
                                   account_non_expired BOOLEAN DEFAULT TRUE,
                                   credentials_non_expired BOOLEAN DEFAULT TRUE,
                                   account_non_locked BOOLEAN DEFAULT TRUE,
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   deleted_at TIMESTAMP NULL
);

CREATE TABLE car_rentals.authorities (
                                         id CHAR(36) NOT NULL PRIMARY KEY DEFAULT (UUID()),
                                         user_id CHAR(36) NOT NULL,
                                         authority VARCHAR(50) NOT NULL,
                                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                         deleted_at TIMESTAMP NULL,
                                         CONSTRAINT fk_authorities_users FOREIGN KEY(user_id) REFERENCES users(id)
);
