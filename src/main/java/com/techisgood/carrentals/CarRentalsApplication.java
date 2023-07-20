package com.techisgood.carrentals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = "com.techisgood.carrentals.repository")
public class CarRentalsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalsApplication.class, args);
    }

}
