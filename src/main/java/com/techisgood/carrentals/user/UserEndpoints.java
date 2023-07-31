package com.techisgood.carrentals.user;

import com.techisgood.carrentals.exception.RemoteServiceException;
import com.techisgood.carrentals.model.DbUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/v1/user")
public class UserEndpoints {

	private final UserCreateDemographicsService userCreateDemographicsService;
	private final UserRepository userRepository;

	@GetMapping("/current")
	public ResponseEntity<UserDto> getLoggedInUser(@AuthenticationPrincipal UserDetails userDetails) {
		DbUser dbUser = userRepository.findUserWithActiveInsurancesAndLicenses(userDetails.getUsername()).orElseThrow();
		return ResponseEntity.ok(UserDto.from(dbUser));
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
