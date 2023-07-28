ALTER TABLE car_rentals.rentals
    ADD COLUMN status varchar(50) not null

;

ALTER TABLE car_rentals.rental_pictures
    modify s3_url varchar(500) null;