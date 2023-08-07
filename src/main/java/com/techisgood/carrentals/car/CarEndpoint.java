package com.techisgood.carrentals.car;

import com.techisgood.carrentals.authorities.UserAuthority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techisgood.carrentals.model.Car;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/staff/v1/car")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_STAFF') || hasAuthority('ROLE_ADMIN')")
public class CarEndpoint {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<CarDto> createOrUpdateCar(@Valid @RequestBody CarCreationDto carCreationDto) {
        Car car = carService.createOrUpdateCar(carCreationDto);
        return new ResponseEntity<>(CarDto.from(car), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_CLERK') || hasAuthority('ROLE_STAFF') || hasAuthority('ROLE_ADMIN')")
    public Page<CarDto> findAllCarsByLocation(Pageable pageable, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails.getAuthorities().stream().anyMatch(x -> x.getAuthority().equals(UserAuthority.ROLE_ADMIN.name()))) {
            return carService.findAllCars(pageable);
        }
        return carService.findAllCarsByLocation(pageable, userDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable String id) {
        CarDto carDto = carService.get(id);
        return ResponseEntity.ok(carDto);
    }
}

