package com.techisgood.carrentals.car;

import com.techisgood.carrentals.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/staff/v1/car")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_STAFF') || hasAuthority('ROLE_ADMIN')")
public class CarEndpoint {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<Car> createOrUpdateCar(@Valid @RequestBody CarCreationDto carCreationDto) {
        Car car = carService.createOrUpdateCar(carCreationDto);
        return new ResponseEntity<>(car, HttpStatus.CREATED);
    }

    @GetMapping
    public Page<CarDto> findAllCars(Pageable pageable) {
        return carService.findAllCars(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable String id) {
        CarDto carDto = carService.get(id);
        return ResponseEntity.ok(carDto);
    }
}

