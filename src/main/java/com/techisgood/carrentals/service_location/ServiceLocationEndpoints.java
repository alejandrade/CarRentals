package com.techisgood.carrentals.service_location;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.techisgood.carrentals.model.ServiceLocation;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/staff/v1/serviceLocations")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_STAFF') || hasAuthority('ROLE_ADMIN')")
public class ServiceLocationEndpoints {

	private final ServiceLocationService serviceLocationService;
	private final ServiceLocationRepository serviceLocationRepository;

	@PostMapping
	public ServiceLocationDto save(@Valid @RequestBody ServiceLocationDto dto) {
		return serviceLocationService.save(dto);
	}

	@GetMapping("/byname")
	public ResponseEntity<?> getAllServiceLocationsByName(
			@RequestParam(required=false) String state,
			@RequestParam(required=false) String name,
			Pageable pageable) {

		Page<ServiceLocation> result =
				serviceLocationService.getServiceLocationByStateAndName(name, state, pageable);
		return ResponseEntity.ok().body(result.stream().map(ServiceLocationDto::from).toList());
	}
	
	@GetMapping()
	public ResponseEntity<?> getAllServiceLocations(
			@RequestParam(required=false) String state,
			Pageable pageable) {
		
		if (state != null && (state.isBlank() || state.isEmpty())) state = null;
		Page<ServiceLocation> result = serviceLocationService.getServiceLocations(null, state, pageable);
		return ResponseEntity.ok().body(result.stream().map(ServiceLocationDto::from).toList());
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllServiceLocations() {

		List<ServiceLocation> result = serviceLocationService.getAll();
		return ResponseEntity.ok().body(result.stream().map(ServiceLocationDto::from).toList());
	}
	
	@GetMapping("/{id}")
	public ServiceLocationDto getServiceLocationBy(@PathVariable String id) {
		return serviceLocationRepository.findById(id).map(ServiceLocationDto::from).orElseThrow();
	}

	@GetMapping("/user/{id}")
	public ServiceLocationDto getServiceLocationByUser(@PathVariable String id) {
		return serviceLocationRepository.byClerkId(id).map(ServiceLocationDto::from).orElseThrow();
	}
	
}
