package com.techisgood.carrentals.car;

import com.techisgood.carrentals.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    @Transactional
    public Car createOrUpdateCar(CarCreationDto carCreationDto) {
        // Check if a car with the given VIN exists
        Car car = carRepository.findByVin(carCreationDto.getVin()).orElse(new Car());

        car.setMake(carCreationDto.getMake());
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

}
