package com.techisgood.carrentals.rentals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techisgood.carrentals.car.CarRepository;
import com.techisgood.carrentals.model.Car;
import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.model.Rental;
import com.techisgood.carrentals.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RenalService {

	private final RentalRepository rentalRepository;
	private final CarRepository carRepository;
	private final UserRepository userRepository;
	
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
	
}
