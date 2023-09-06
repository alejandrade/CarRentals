package com.techisgood.carrentals.car;


import com.techisgood.carrentals.model.Car;
import com.techisgood.carrentals.rentals.RentalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends PagingAndSortingRepository<Car, String>, JpaRepository<Car, String> {
    Optional<Car> findByVin(String vin);
    Optional<Car> findByShortId(String shortId);

    @Query("SELECT c FROM Car c " +
            "WHERE (c.status = 'ACTIVE') and c.serviceLocation.id = :locationId")
    Page<Car> findAllByLocationId(@Param("locationId") String locationId, Pageable pageable);


}
