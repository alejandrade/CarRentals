package com.techisgood.carrentals.service_location;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techisgood.carrentals.model.Car;
import com.techisgood.carrentals.model.DbUser;
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
	public ServiceLocation createServiceLocation(ServiceLocationCreateDto serviceLocation) {
		ServiceLocation sl = new ServiceLocation();
		sl.setName(serviceLocation.getName());
		sl.setAddress(serviceLocation.getAddress());
		sl.setCity(serviceLocation.getCity());
		sl.setState(serviceLocation.getState());
		sl.setPostalCode(serviceLocation.getPostalCode());
		sl.setCountry(serviceLocation.getCountry());
		sl.setAdditionalInfo(serviceLocation.getAdditionalInfo());
		
		sl = serviceLocationRepository.save(sl);
		
		sl.setCars(new ArrayList<Car>());
		sl.setClerks(new ArrayList<DbUser>());
		
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
	
	
	
	@Transactional
	public ServiceLocation addClerk(ServiceLocation serviceLocation, DbUser clerk) {
		
		boolean found = false;
		for (DbUser u : serviceLocation.getClerks()) {
			if (u.getId() == clerk.getId()) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			serviceLocation.getClerks().add(clerk);
			serviceLocationRepository.save(serviceLocation);
		}
		
		return serviceLocation;
	}
	
	
	@Transactional
	public ServiceLocation addCar(ServiceLocation serviceLocation, Car car) {
		
		boolean found = false;
		for (Car c : serviceLocation.getCars()) {
			if (c.getId() == car.getId()) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			serviceLocation.getCars().add(car);
			serviceLocationRepository.save(serviceLocation);
		}
		
		return serviceLocation;
	}
	
}
