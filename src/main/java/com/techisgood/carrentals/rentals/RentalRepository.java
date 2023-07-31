package com.techisgood.carrentals.rentals;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techisgood.carrentals.model.Rental;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, String>, PagingAndSortingRepository<Rental, String> {
    List<Rental> findByCar_ShortId(String shortId);
    @Query("SELECT r FROM Rental r WHERE r.clerk.id = :clerkId")
    Page<Rental> findAllByClerkId(@Param("clerkId") String clerkId, Pageable pageable);
    
    @Query("SELECT r FROM Rental r WHERE r.serviceLocation.id = :serviceLocationId")
    Page<Rental> findAllByServiceLocationId(@Param("serviceLocationId") String serviceLocaitonId, Pageable pageable);
}
