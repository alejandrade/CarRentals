SET foreign_key_checks = 0;

drop table car_rentals.service_location_clerk;
-- auto-generated definition
create table service_location_clerk
(
    location_id char(36)                            not null,
    user_id     char(36)                            not null,
    first_name  varchar(100)                        null,
    last_name   varchar(100)                        null,
    status      varchar(36)                         null,
    created_by  char(36)                            not null,
    created_at  timestamp default CURRENT_TIMESTAMP not null,
    updated_by  char(36)                            null,
    updated_at  timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    id          varchar(36)                         not null
        primary key,
    version     int                                 null,
    constraint service_location_clerk_key
        unique (location_id),
    constraint service_location_clerk_location_id
        foreign key (location_id) references service_location (id),
    constraint service_location_clerk_user_id
        foreign key (user_id) references users (id)
);

SET foreign_key_checks = 1;
