package com.techisgood.carrentals.service_location;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techisgood.carrentals.model.ServiceLocation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceLocationService {
	
	private final ServiceLocationRepository serviceLocationRepository;
	
	public ArrayList<ServiceLocation> getServiceLocations(String id, String state) {
		ArrayList<ServiceLocation> list = new ArrayList<ServiceLocation>();
		if (id != null) {
			Optional<ServiceLocation> osl = serviceLocationRepository.findById(id);
			if (osl.isPresent()) list.add(osl.get());
		}
		else if (state != null) {
			list = (ArrayList<ServiceLocation>)serviceLocationRepository.findAllByState(state);
		}
		else {
			list = (ArrayList<ServiceLocation>)serviceLocationRepository.findAll();
		}
		return list;
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
