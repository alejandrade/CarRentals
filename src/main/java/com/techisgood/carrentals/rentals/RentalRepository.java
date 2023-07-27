package com.techisgood.carrentals.rentals;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techisgood.carrentals.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, String> {

}
