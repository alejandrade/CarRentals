package com.techisgood.carrentals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CarRentalsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalsApplication.class, args);
    }

}
