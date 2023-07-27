package com.techisgood.carrentals.car;


import com.techisgood.carrentals.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CarRepository extends PagingAndSortingRepository<Car, String>, JpaRepository<Car, String> {
    Optional<Car> findByVin(String vin);
}
