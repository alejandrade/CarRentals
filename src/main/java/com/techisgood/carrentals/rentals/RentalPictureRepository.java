package com.techisgood.carrentals.rentals;

import com.techisgood.carrentals.model.Rental;
import com.techisgood.carrentals.model.RentalPicture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentalPictureRepository extends JpaRepository<RentalPicture, String>, PagingAndSortingRepository<RentalPicture, String> {
    
}
