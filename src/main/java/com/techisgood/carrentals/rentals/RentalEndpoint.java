package com.techisgood.carrentals.rentals;

import com.techisgood.carrentals.car.CarRepository;
import com.techisgood.carrentals.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/clerk/v1/rentals")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_CLERK') || hasAuthority('ROLE_ADMIN')")
public class RentalEndpoint {
    private final RentalService rentalService;
    private final CarRepository carRepository;
    private final RentalPictureService rentalPictureService;  // Inject the RentalPictureService

    // POST endpoint to create a new rental
    @PostMapping
    public ResponseEntity<RentalDto> createRental(@RequestBody @Valid RentalDto dto) {
        Optional<Car> byShortId = carRepository.findByShortId(dto.getShortId());
        if (byShortId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        dto.setCarId(byShortId.get().getId());
        RentalDto createdRental = rentalService.createRentalUsingDto(dto);
        return ResponseEntity.ok(createdRental);
    }

    @PostMapping("/{rentalId}/start")
    public ResponseEntity<RentalDto> startRental(
            @PathVariable String rentalId,
            @RequestBody @Valid RentalActionDto rentalActionDto) {

        RentalDto startedRental = rentalService.startRental(rentalActionDto.getOdometer(), rentalId, rentalActionDto.getVersion());
        return ResponseEntity.ok(startedRental);
    }

    @PostMapping("/{rentalId}/end")
    public ResponseEntity<RentalDto> endRental(
            @PathVariable String rentalId,
            @RequestBody @Valid RentalActionDto rentalActionDto) {

        RentalDto endedRental = rentalService.endRental(rentalActionDto.getOdometer(), rentalId, rentalActionDto.getVersion());
        return ResponseEntity.ok(endedRental);
    }


    // GET endpoint to retrieve all rentals
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Page<RentalDto>> getAllRentals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<RentalDto> rentalDtos = rentalService.getPage(page, size);
        return ResponseEntity.ok(rentalDtos);
    }

    @GetMapping("/byClerk/{clerkId}")
    public ResponseEntity<Page<RentalDto>> getRentalsByClerkId(
            @PathVariable String clerkId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<RentalDto> rentalDtos = rentalService.getRentalsByClerkId(clerkId, page, size);
        return ResponseEntity.ok(rentalDtos);
    }

    @PostMapping("/pictures")
    public ResponseEntity<RentalPictureDto> createRentalPicture(
            @RequestParam("file") MultipartFile file,
            @RequestParam("dto") @Valid CreateRentalPictureDto dto) {
        RentalPictureDto createdRentalPicture = rentalPictureService.createRentalPicture(dto, file);
        return ResponseEntity.ok(createdRentalPicture);
    }
}