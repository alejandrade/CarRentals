package com.techisgood.carrentals.user;

import com.techisgood.carrentals.exception.RemoteServiceException;
import com.techisgood.carrentals.model.DbUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/v1/user")
public class UserEndpoints {

	private final UserCreateDemographicsService userCreateDemographicsService;
	private final ActiveUserDataService activeUserDataService;

	@GetMapping("/current")
	public UserDto getLoggedInUser(@AuthenticationPrincipal UserDetails userDetails) {
		return activeUserDataService.getCurrentUser(userDetails.getUsername());
	}
	
	@PostMapping("/demographic")
	public ResponseEntity<?> createUserDemographics(@Valid @RequestBody UserDemographicsDto requestBody) throws RemoteServiceException {
		userCreateDemographicsService.createUserDemographics(
				requestBody.getUserId(), 
				requestBody.getFirstName(), 
				requestBody.getMiddleInitial(), 
				requestBody.getLastName(), 
				requestBody.getDateOfBirth(), 
				requestBody.getGender(), 
				requestBody.getAddress(), 
				requestBody.getCity(), 
				requestBody.getState(), 
				requestBody.getPostalCode(), 
				requestBody.getCountry(),
				requestBody.getAdditionalInfo()
				);
        return ResponseEntity.ok(requestBody);
	}
}
