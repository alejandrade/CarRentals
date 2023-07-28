package com.techisgood.carrentals.service_location;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techisgood.carrentals.model.ServiceLocation;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/serviceLocations/v1")
@RequiredArgsConstructor
public class ServiceLocationEndpoints {

	private final ServiceLocationService serviceLocationService;
	
	@GetMapping()
	public ResponseEntity<?> getAllServiceLocations() {
		ArrayList<ServiceLocation> locations = serviceLocationService.getServiceLocations(null, null);
		return ResponseEntity.ok().body(locations);
	}
	
}
