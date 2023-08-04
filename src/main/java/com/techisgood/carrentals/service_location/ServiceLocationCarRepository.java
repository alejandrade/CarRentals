package com.techisgood.carrentals.service_location;

import com.techisgood.carrentals.model.ServiceLocationCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceLocationCarRepository
        extends JpaRepository<ServiceLocationCar, String>, PagingAndSortingRepository<ServiceLocationCar, String> {
    Optional<ServiceLocationCar> findByIdCarId(String carId);

    List<ServiceLocationCar> findByIdLocationId(String locationId);
}
