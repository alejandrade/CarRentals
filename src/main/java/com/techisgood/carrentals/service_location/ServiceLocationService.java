package com.techisgood.carrentals.service_location;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techisgood.carrentals.model.ServiceLocation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceLocationService {
	
	private final ServiceLocationRepository serviceLocationRepository;
	
	public Page<ServiceLocation> getServiceLocations(String id, String state, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<ServiceLocation> result = null;
		if (id != null) {
			result = serviceLocationRepository.findAllById(id, pageable);
		}
		else if (state != null) {
			result = serviceLocationRepository.findAllByState(state, pageable);
		}
		else {
			result = serviceLocationRepository.findAll(pageable);
		}
		return result;
	}
	
	@Transactional
	public ServiceLocation createServiceLocation(String name, String address, String city, String state, String postalCode, String country, String additional) {
		ServiceLocation sl = new ServiceLocation();
		sl.setName(name);
		sl.setAddress(address);
		sl.setCity(city);
		sl.setState(state);
		sl.setPostalCode(postalCode);
		sl.setCountry(country);
		sl.setAdditionalInfo(additional);
		
		serviceLocationRepository.save(sl);
		
		return sl;
	}
	
	@Transactional
	public ServiceLocation updateServiceLocation(String id, String name, String address, String city, String state, String postalCode, String country, String additional) {
		Optional<ServiceLocation> osl = serviceLocationRepository.findById(id);
		if (osl.isEmpty()) {
			return null;
		}
		ServiceLocation sl = osl.get();
		sl.setName(name);
		sl.setAddress(address);
		sl.setCity(city);
		sl.setState(state);
		sl.setPostalCode(postalCode);
		sl.setCountry(country);
		sl.setAdditionalInfo(additional);
		serviceLocationRepository.save(sl);
		
		return sl;
	}
	
	
	@Transactional
	public ServiceLocation deleteServiceLocation(String id) {
		Optional<ServiceLocation> osl = serviceLocationRepository.findById(id);
		if (osl.isEmpty()) {
			return null;
		}
		ServiceLocation sl = osl.get();
		serviceLocationRepository.delete(sl);
		return sl;
	}
	
}
