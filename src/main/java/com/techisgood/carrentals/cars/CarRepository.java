package com.techisgood.carrentals.cars;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techisgood.carrentals.model.Car;

public interface CarRepository extends JpaRepository<Car, String> {

}
