package com.techisgood.carrentals.admin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techisgood.carrentals.authorities.UserAuthority;
import com.techisgood.carrentals.car.CarCreationDto;
import com.techisgood.carrentals.car.CarService;
import com.techisgood.carrentals.model.Car;
import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.model.ServiceLocation;
import com.techisgood.carrentals.rentals.RentalCreateDto;
import com.techisgood.carrentals.rentals.RentalService;
import com.techisgood.carrentals.rentals.RentalStatus;
import com.techisgood.carrentals.service_location.ServiceLocationCreateDto;
import com.techisgood.carrentals.service_location.ServiceLocationService;
import com.techisgood.carrentals.user.UserCreateIfNotExistServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/v1/debug")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminDebugEndpoints {

	private final CarService carService;
	private final ServiceLocationService serviceLocationService;
	private final RentalService rentalService;
	private final UserCreateIfNotExistServiceImpl userCreateService;
	
	@GetMapping
	public ResponseEntity<?> test() {
		return ResponseEntity.ok("GOOD");
	}
	
	@GetMapping("/create-initial-debug-data")
	public ResponseEntity<?> createInitialDebugData() {
		

		ServiceLocationCreateDto serviceLocationCreation = new ServiceLocationCreateDto(
				"Cool Car Workshop",
				"100 Wall St.",
				"New York",
				"NY",
				"10001",
				"US",
				"This place is great!"
				);
		ServiceLocation serviceLocation = serviceLocationService.createServiceLocation(serviceLocationCreation);
		
		CarCreationDto carCreation = new CarCreationDto(
				"Honda", 
				new BigDecimal(20.00), 
				"Civic", 
				2015, 
				"12345678912345678", 
				"Red",
				new BigDecimal(1000.00), 
				new BigDecimal(10000.00), 
				true, 
				"ABC123", 
				"awesome", 
				0);
		Car car = carService.createOrUpdateCar(carCreation);
		serviceLocationService.addCar(serviceLocation, car);
		
		DbUser clerk = userCreateService.createIfNoneExists("+19285551000", UserAuthority.ROLE_CLERK);
		serviceLocationService.addClerk(serviceLocation, clerk);
		
		RentalCreateDto rentalCreation = new RentalCreateDto(
				car.getShortId(), 
				clerk.getId(), 
				serviceLocation.getId(),
				clerk.getPhoneNumber(), 
				RentalStatus.DATA_ENTRY, 
				car.getMileage(), 
				LocalDateTime.now(), 
				0);
		rentalService.createRentalUsingDto(car.getId(), rentalCreation);
		
		return ResponseEntity.ok("DONE");
	}
}
