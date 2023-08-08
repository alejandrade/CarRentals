package com.techisgood.carrentals.service_location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.techisgood.carrentals.security.JwtTokenProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.techisgood.carrentals.model.Car;
import com.techisgood.carrentals.model.ServiceLocation;
import com.techisgood.carrentals.model.ServiceLocationClerk;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ServiceLocationService {
	private final JwtTokenProvider jwtTokenProvider;

	private final ServiceLocationRepository serviceLocationRepository;
	public ServiceLocationDto save(ServiceLocationDto dto) {
		ServiceLocation serviceLocation;
		if (dto.getId() == null) {
			serviceLocation = new ServiceLocation();
		} else {
			serviceLocation = serviceLocationRepository.findById(dto.getId()).orElseThrow();
		}
		serviceLocation.setName(dto.getName());
		serviceLocation.setCountry("US");
		serviceLocation.setCity(dto.getCity());
		serviceLocation.setState(dto.getState());
		serviceLocation.setAdditionalInfo(dto.getAdditionalInfo());
		serviceLocation.setPostalCode(dto.getPostalCode());
		serviceLocation.setAddress(dto.getAddress());
		serviceLocation.setVersion(dto.getVersion());
		serviceLocationRepository.save(serviceLocation);
		return ServiceLocationDto.from(serviceLocation);
	}

	public List<ServiceLocation> getAll() {
		return serviceLocationRepository.findAll();
	}
	public Page<ServiceLocation> getServiceLocationByStateAndName(String name, String state, Pageable page) {
		return serviceLocationRepository.findAllByNameStartsWithAndStateEquals(name, state, page);
	}
	
	public Page<ServiceLocation> getServiceLocations(String id, String state, Pageable page) {
		Page<ServiceLocation> result = null;
		if (id != null) {
			result = serviceLocationRepository.findAllById(id, page);
		}
		else if (state != null) {
			result = serviceLocationRepository.findAllByState(state, page);
		}
		else {
			result = serviceLocationRepository.findAll(page);
		}
		return result;
	}

	@Transactional(readOnly = true)
	public ServiceLocation currentUserLocation() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userIdFromJWT = jwtTokenProvider.getUserIdFromJWT(authentication.getCredentials().toString());
		return serviceLocationRepository.byClerkId(userIdFromJWT).orElseThrow();
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
		sl.setClerks(new ArrayList<ServiceLocationClerk>());
		
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
	public ServiceLocation addClerk(ServiceLocation serviceLocation, ServiceLocationClerk clerk) {
		
		boolean found = false;
		for (ServiceLocationClerk c : serviceLocation.getClerks()) {
			if (c.getId() == clerk.getId()) {
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
