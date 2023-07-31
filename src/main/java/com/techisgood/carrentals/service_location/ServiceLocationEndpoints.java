package com.techisgood.carrentals.service_location;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techisgood.carrentals.model.ServiceLocation;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/staff/v1/serviceLocations")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_STAFF') || hasAuthority('ROLE_ADMIN')")
public class ServiceLocationEndpoints {

	private final ServiceLocationService serviceLocationService;
	
	@GetMapping()
	public ResponseEntity<?> getAllServiceLocations(
			@RequestParam(required=false) String state, 
			@RequestParam(required=false, defaultValue="0") Integer page, 
			@RequestParam(required=false, defaultValue="10") Integer size) {
		
		if (state != null && (state.isBlank() || state.isEmpty())) state = null;
		Page<ServiceLocation> result = serviceLocationService.getServiceLocations(null, state, page, size);
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getServiceLocationBy(@PathVariable String id) {
		Page<ServiceLocation> locations = serviceLocationService.getServiceLocations(id, null, 0, 10);
		return ResponseEntity.ok().body(locations);
	}
	
}
