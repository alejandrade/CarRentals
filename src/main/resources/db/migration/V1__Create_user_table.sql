CREATE TABLE car_rentals.users (
                       id CHAR(36) NOT NULL PRIMARY KEY DEFAULT (UUID()),
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL,
                       enabled BOOLEAN NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       deleted_at TIMESTAMP NULL
);

CREATE TABLE car_rentals.authorities (
                             id CHAR(36) NOT NULL PRIMARY KEY DEFAULT (UUID()),
                             username VARCHAR(50) NOT NULL,
                             authority VARCHAR(50) NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             deleted_at TIMESTAMP NULL,
                             CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);