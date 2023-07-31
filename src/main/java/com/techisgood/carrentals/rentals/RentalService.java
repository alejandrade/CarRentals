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

import com.techisgood.carrentals.car.CarRepository;
import com.techisgood.carrentals.model.Car;
import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.model.Rental;
import com.techisgood.carrentals.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RentalService {

	private final RentalRepository rentalRepository;
	private final CarRepository carRepository;
	private final UserRepository userRepository;


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
	public RentalDto createRentalUsingDto(RentalDto dto) throws IllegalArgumentException {
		Rental rental = createRental(dto.getCarId(), dto.getRenterId(), dto.getEndingOdometerReading(), dto.getRentalDatetime());
		return RentalDto.from(rental);
	}

	@Transactional
	public Rental createRental(String carId, String renterId, BigDecimal odometer, LocalDateTime rentalDateTime) throws IllegalArgumentException {
		Optional<Car> car = carRepository.findById(carId);
		if (car.isEmpty()) {
			throw new IllegalArgumentException("car_id");
		}
		Optional<DbUser> renter = userRepository.findById(renterId);
		if (renter.isEmpty()) {
			throw new IllegalArgumentException("renter_id");
		}
		
		Rental rental = new Rental();
		rental.setCar(car.get());
		rental.setRenter(renter.get());
		rental.setInitialOdometerReading(odometer);
		rental.setRentalDatetime(rentalDateTime);
		
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
