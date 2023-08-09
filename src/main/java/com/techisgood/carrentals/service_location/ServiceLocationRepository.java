package com.techisgood.carrentals.service_location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.techisgood.carrentals.model.ServiceLocation;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ServiceLocationRepository extends JpaRepository<ServiceLocation, String>, PagingAndSortingRepository<ServiceLocation, String> {
	@Query("SELECT s FROM ServiceLocation s WHERE s.id = :id")
	public Page<ServiceLocation> findAllById(String id, Pageable pageable); 
    @Query("SELECT s FROM ServiceLocation s WHERE s.state = :state")
	public Page<ServiceLocation> findAllByState(String state, Pageable pageable);

	@Query("SELECT s FROM ServiceLocation s WHERE s.name LIKE CONCAT(:namePrefix, '%') AND s.state = :state")
	Page<ServiceLocation> findAllByNameStartsWithAndStateEquals(String namePrefix, String state, Pageable pageable);

	@Query("SELECT s FROM ServiceLocation s join ServiceLocationClerk c on c.serviceLocation = s where c.user.id = :clerkId")
	Optional<ServiceLocation> byClerkId(String clerkId);

}
