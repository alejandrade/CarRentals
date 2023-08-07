package com.techisgood.carrentals.car;

import com.techisgood.carrentals.model.*;
import com.techisgood.carrentals.service_location.ServiceLocationCarRepository;
import com.techisgood.carrentals.service_location.ServiceLocationRepository;
import com.techisgood.carrentals.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final ServiceLocationRepository serviceLocationRepository;
    private final ServiceLocationCarRepository serviceLocationCarRepository;
    private final UserRepository userRepository;

    @Transactional
    public Car createOrUpdateCar(@Valid CarCreationDto carCreationDto) {
        // Check if a car with the given VIN exists
        Car car = carRepository.findByVin(carCreationDto.getVin()).orElse(new Car());
        ServiceLocation serviceLocation = serviceLocationRepository.findById(carCreationDto.getServiceLocationId()).orElseThrow();

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
        ServiceLocationCar slc;
        if (car.getId() != null) {
            Optional<ServiceLocationCar> byIdCarId = serviceLocationCarRepository.findByIdCarId(car.getId());
            slc = byIdCarId.orElse(new ServiceLocationCar());
        } else {
            slc = new ServiceLocationCar();
        }

        Car savedCar = carRepository.save(car);
        slc.setCar(savedCar);
        slc.setServiceLocation(serviceLocation);
        serviceLocationCarRepository.save(slc);
        return savedCar;
    }

    @Transactional(readOnly = true)
    public Page<CarDto> findAllCars(Pageable pageable) {
        Page<Car> cars = carRepository.findAll(pageable);
        return cars.map(CarDto::from);
    }

    @Transactional(readOnly = true)
    public Page<CarDto> findAllCarsByLocation(Pageable pageable, UserDetails userDetails) {
        DbUser user = userRepository.findById(userDetails.getUsername()).orElseThrow();
        List<ServiceLocationClerk> serviceLocationClerks = user.getServiceLocationClerks();
        Page<Car> cars = carRepository.findAllByLocationId(serviceLocationClerks.stream().findAny().orElseThrow().getServiceLocationId(), pageable);
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
