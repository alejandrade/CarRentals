package com.techisgood.carrentals.service_location;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techisgood.carrentals.model.ServiceLocation;

public interface ServiceLocationRepository extends JpaRepository<ServiceLocation, String> {
	public List<ServiceLocation> findAllByState(String state); 
}
