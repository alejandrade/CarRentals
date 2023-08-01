package com.techisgood.carrentals.car;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techisgood.carrentals.model.Car;
import com.techisgood.carrentals.model.ServiceLocation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    @Transactional
    public Car createOrUpdateCar(@Valid CarCreationDto carCreationDto) {
        // Check if a car with the given VIN exists
        Car car = carRepository.findByVin(carCreationDto.getVin()).orElse(new Car());

        car.setMake(carCreationDto.getMake());
        car.setRentPrice(carCreationDto.getRentPrice());
        car.setModel(carCreationDto.getModel());
        car.setYear(carCreationDto.getYear());
        car.setVin(carCreationDto.getVin());
        car.setColor(carCreationDto.getColor());
        car.setMileage(carCreationDto.getMileage());
        car.setPrice(carCreationDto.getPrice());
        car.setAvailability(carCreationDto.getAvailability());
        car.setLicensePlate(carCreationDto.getLicensePlate());
        car.setStatus(carCreationDto.getStatus());
        car.setVersion(carCreationDto.getVersion());

        return carRepository.save(car);
    }

    @Transactional(readOnly = true)
    public Page<CarDto> findAllCars(Pageable pageable) {
        Page<Car> cars = carRepository.findAll(pageable);
        return cars.map(CarDto::from);
    }

    @Transactional(readOnly = true)
    public CarDto get(String id) {
        return carRepository.findById(id).map(CarDto::from).orElse(null);
    }

    
    
    @Transactional
    public Car updateServiceLocation(Car car, ServiceLocation newLocation) {
    	//TODO: This should move the car from one service location to the other. (table= car_service_location)
    	//the service end date should update where the columns match car and a null service end date.
    	//a new entry should be added for car and new service location.
    	return car;
    }
    
}
