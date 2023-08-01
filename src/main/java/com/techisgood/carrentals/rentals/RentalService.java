package com.techisgood.carrentals.rentals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techisgood.carrentals.car.CarRepository;
import com.techisgood.carrentals.model.Car;
import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.model.Rental;
import com.techisgood.carrentals.model.ServiceLocation;
import com.techisgood.carrentals.service_location.ServiceLocationRepository;
import com.techisgood.carrentals.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentalService {

	private final RentalRepository rentalRepository;
	private final CarRepository carRepository;
	private final UserRepository userRepository;
	private final ServiceLocationRepository serviceLocationRepository;

	@Transactional
	public RentalDto startRental(BigDecimal odometer, String rentalId, Integer version) {
		Rental byId = rentalRepository.findById(rentalId).orElseThrow();
		byId.setStatus(RentalStatus.RENTED);
		byId.setInitialOdometerReading(odometer);
		byId.setRentalDatetime(LocalDateTime.now());
		byId.setVersion(version);
		return RentalDto.from(rentalRepository.save(byId));
	}

	@Transactional
	public RentalDto endRental(BigDecimal odometer, String rentalId, Integer version) {
		Rental byId = rentalRepository.findById(rentalId).orElseThrow();
		byId.setStatus(RentalStatus.RETURNED);
		byId.setEndingOdometerReading(odometer);
		byId.setReturnDatetime(LocalDateTime.now());
		byId.setVersion(version);
		return RentalDto.from(rentalRepository.save(byId));
	}

	@Transactional
	public RentalDto createRentalUsingDto(String carId, RentalCreateDto dto) throws IllegalArgumentException {
		DbUser dbUser = userRepository.findByPhoneNumber(dto.getRenterPhoneNumber()).orElseThrow();
		Rental rental = createRental(
				carId, 
				dbUser.getId(),
				dto.getClerkId(),
				dto.getServiceLocationId(),
				dto.getInitialOdometerReading(), 
				dto.getRentalDatetime(),
				dto.getStatus());
		return RentalDto.from(rental);
	}

	@Transactional
	public Rental createRental(
			String carId, 
			String renterId, 
			String clerkId,
			String serviceLocationId,
			BigDecimal odometer, 
			LocalDateTime rentalDateTime,
			RentalStatus status) throws IllegalArgumentException {
		Optional<Car> car = carRepository.findById(carId);
		if (car.isEmpty()) {
			throw new IllegalArgumentException("car_id");
		}
		Optional<DbUser> renter = userRepository.findById(renterId);
		if (renter.isEmpty()) {
			throw new IllegalArgumentException("renter_id");
		}
		Optional<DbUser> clerk = userRepository.findById(clerkId);
		if (clerk.isEmpty()) {
			throw new IllegalArgumentException("clerk_id");
		}
		Optional<ServiceLocation> serviceLocation = serviceLocationRepository.findById(serviceLocationId);
		if (serviceLocation.isEmpty()) {
			throw new IllegalArgumentException("service_location_id");
		}
		
		Rental rental = new Rental();
		rental.setCar(car.get());
		rental.setClerk(clerk.get());
		rental.setRenter(renter.get());
		rental.setServiceLocation(serviceLocation.get());
		rental.setInitialOdometerReading(odometer);
		rental.setRentalDatetime(rentalDateTime);
		rental.setStatus(status);
		
		rentalRepository.save(rental);
		return rental;
	}


	@Transactional(readOnly = true)
	public List<RentalDto> findRentalsByCarShortId(String carId) {
		return rentalRepository.findByCar_ShortId(carId).stream()
				.map(RentalDto::from)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Page<RentalDto> getPage(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Rental> rentalPage = rentalRepository.findAll(pageable);
		return rentalPage.map(RentalDto::from);
	}

	@Transactional(readOnly = true)
	public Page<RentalDto> getRentalsByClerkId(String clerkId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Rental> rentalsByClerk = rentalRepository.findAllByClerkId(clerkId, pageable);
		return rentalsByClerk.map(RentalDto::from);
	}
	
	@Transactional(readOnly = true)
	public Page<RentalDto> getRentalsByServiceLocationId(String serviceLocationId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Rental> rentalsByClerk = rentalRepository.findAllByServiceLocationId(serviceLocationId, pageable);
		return rentalsByClerk.map(RentalDto::from);
	}
}
