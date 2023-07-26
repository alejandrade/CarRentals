package com.techisgood.carrentals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaAuditing
@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = "com.techisgood.carrentals")
public class CarRentalsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalsApplication.class, args);
    }

}
