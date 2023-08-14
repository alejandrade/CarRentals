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

    @Query("SELECT r FROM Rental r WHERE r.clerk.id = :clerkId AND r.status = :status")
    Page<Rental> findAllByClerkIdAndStatus(@Param("clerkId") String clerkId, @Param("status") RentalStatus status, Pageable pageable);

    @Query("select r from Rental r where r.paid = false AND r.status = com.techisgood.carrentals.rentals.RentalStatus.RETURNED AND r.serviceLocation.id = :serviceLocationId")
    Page<Rental> getAllUnpaidRentals(Pageable pageable, @Param("serviceLocationId") String serviceLocaitonId0);


    @Query("SELECT r FROM Rental r WHERE r.id = :id AND r.status = :status")
    Rental findByIdAndStatus(@Param("id") String id, @Param("status") RentalStatus status);

    @Query("SELECT r FROM Rental r WHERE r.status = :status")
    Page<Rental> findAllByStatus(@Param("status") RentalStatus status, Pageable pageable);
}
