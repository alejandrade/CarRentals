alter table car_rentals.cars
    add picture_key varchar(255) null;

alter table car_rentals.user_insurance
    change front_card_picture front_card_picture_key varchar(255) null;

alter table car_rentals.user_insurance
    change back_card_picture back_card_picture_key varchar(255) null;

alter table car_rentals.user_licenses
    change back_card_picture back_card_picture_key varchar(255) null;

alter table car_rentals.user_licenses
    change front_card_picture front_card_picture_key varchar(255) null;

