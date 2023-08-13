package com.techisgood.carrentals.rentals;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.techisgood.carrentals.model.Rental;
import com.techisgood.carrentals.security.JwtTokenProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techisgood.carrentals.car.CarRepository;
import com.techisgood.carrentals.model.Car;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clerk/v1/rentals")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_CLERK') || hasAuthority('ROLE_ADMIN')")
public class RentalEndpoint {
    private final RentalService rentalService;
    private final RentalPictureService rentalPictureService;  // Inject the RentalPictureService
    private final RentalRepository rentalRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // POST endpoint to create a new rental
    @PostMapping
    public ResponseEntity<RentalDto> createRental(@RequestBody @Valid RentalCreateDto dto) {
        Rental createdRental = rentalService.createRentalUsingDto(dto.getCarId(), dto);
        return ResponseEntity.ok(RentalDto.from(createdRental));
    }

    @GetMapping("/{rentalId}")
    public RentalDto getRental(@PathVariable String rentalId) {
        return rentalRepository.findById(rentalId)
                .map(RentalDto::from)
                .orElseThrow();
    }

    @GetMapping("/current/{status}")
    public Page<RentalDto> getRentals(@PathVariable RentalStatus status, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userIdFromJWT = jwtTokenProvider.getUserIdFromJWT(authentication.getCredentials().toString());
        return rentalRepository.findAllByClerkIdAndStatus(userIdFromJWT, status, pageable)
                .map(RentalDto::from);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancel(@PathVariable String id) {
        rentalService.cancel(id);
        return ResponseEntity.ok(Map.of("canceled", true));
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
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required=false)String clerkId,
            @RequestParam(required=false)String serviceLocationId) {
    	Page<RentalDto> rentalDtos = null;
    	if (clerkId != null) {
            rentalDtos = rentalService.getRentalsByClerkId(clerkId, page, size);
    	}
    	else if (serviceLocationId != null) {
    		rentalDtos = rentalService.getRentalsByServiceLocationId(serviceLocationId, page, size);
    	}
    	else {
    		rentalDtos = rentalService.getPage(page, size);
    	}
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
