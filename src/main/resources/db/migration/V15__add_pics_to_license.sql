ALTER TABLE car_rentals.user_licenses
    ADD version INT NOT NULL DEFAULT 1,
    ADD active BOOLEAN DEFAULT TRUE,
    ADD back_card_picture VARCHAR(255) NULL,
    ADD front_card_picture VARCHAR(255) NULL,
    ADD created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ADD deleted_at TIMESTAMP NULL,
    ADD created_by CHAR(36) not null,
    ADD updated_by CHAR(36) null;
CREATE TABLE user_insurance (
                                id CHAR(36) NOT NULL PRIMARY KEY DEFAULT (UUID()),
                                user_id CHAR(36) NOT NULL,
                                policy_number VARCHAR(50) NOT NULL,
                                provider VARCHAR(100) NOT NULL,
                                front_card_picture VARCHAR(255) NULL,
                                back_card_picture varchar(255) NULL,
                                end_date datetime NOT NULL,
                                active BOOLEAN not null,
                                created_by CHAR(36) not null,
                                updated_by CHAR(36) null,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                version INT NOT NULL DEFAULT 1,
                                FOREIGN KEY (user_id) REFERENCES car_rentals.users(id) ON DELETE CASCADE ON UPDATE CASCADE

);
