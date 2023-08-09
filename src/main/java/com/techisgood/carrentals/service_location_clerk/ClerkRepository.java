package com.techisgood.carrentals.service_location_clerk;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techisgood.carrentals.model.ServiceLocationClerk;

public interface ClerkRepository extends JpaRepository<ServiceLocationClerk, String> {
	 Optional<ServiceLocationClerk> findByUserId(String userId);
}
