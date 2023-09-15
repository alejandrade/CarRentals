package com.techisgood.carrentals.service_location_clerk;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techisgood.carrentals.model.ServiceLocationClerk;
import org.springframework.data.jpa.repository.Query;

public interface ServiceLocationClerkRepository extends JpaRepository<ServiceLocationClerk, String> {
	 Optional<ServiceLocationClerk> findByUserId(String userId);

	@Query("SELECT DISTINCT slc.serviceLocation.id FROM ServiceLocationClerk slc WHERE slc.user.id IN :userIds")
	Set<String> findLocationIdsByUserIds(Collection<String> userIds);

}
