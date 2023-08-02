package com.techisgood.carrentals.user;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.techisgood.carrentals.exception.RemoteServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/v1/user")
public class UserEndpoints {

	private final UserCreateDemographicsService userCreateDemographicsService;
	private final ActiveUserDataService activeUserDataService;
	private final UserUpdateContactInformationService userUpdateContactInformationService;
	private final CreateUserInsuranceService createUserInsuranceService;
	private final CreateUserLicenseService createUserLicenseService;
	private final UserService userService;

	@PostMapping("/admin/{userId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public UserDto modifyUser(@PathVariable String userId, @Valid @RequestBody UserDto userDto) {
		return userService.modifyUser(userId, userDto);
	}

	@GetMapping("/admin/{userId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public UserDto getUser(@PathVariable String userId) {
		return userService.getUser(userId);
	}

	@GetMapping("/current")
	public UserDto getLoggedInUser(@AuthenticationPrincipal UserDetails userDetails) {
		return activeUserDataService.getCurrentUser(userDetails.getUsername());
	}

	@PostMapping("/contactInformation")
	public UserDto updateContactInformation(@RequestBody @Valid UpdateContactInformation updateContactInformation) {
		return userUpdateContactInformationService.update(updateContactInformation);
	}

	@PostMapping("/insurance")
	public UserInsuranceDto createInsurance(@Valid @RequestBody UserInsuranceDto userInsuranceDto) {
		return UserInsuranceDto.from(createUserInsuranceService.save(userInsuranceDto));
	}


	@PostMapping("/insurance/upload")
	public ResponseEntity<?> uploadInsuranceImage(
			@RequestParam("insuranceId") String insuranceId,
			@RequestParam("imageAngle") ImageAngle imageAngle,
			@RequestParam("image") MultipartFile imageFile) throws Exception {

		if (imageFile.isEmpty()) {
			return ResponseEntity.badRequest().body(Collections.singletonMap("image", "failed"));
		}
		// Assuming you have a service to handle this upload.
		createUserInsuranceService.uploadInsuranceImage(insuranceId, imageFile, imageAngle);
		return ResponseEntity.ok(Collections.singletonMap("image", "success"));
	}

	@PostMapping("/license")
	public UserLicenseDto createLicense(@Valid @RequestBody UserLicenseDto userInsuranceDto) {
		return UserLicenseDto.from(createUserLicenseService.save(userInsuranceDto));
	}

	@PostMapping("/license/upload")
	public ResponseEntity<?> uploadLicenseImage(
			@RequestParam("licenseId") String licenseId,
			@RequestParam("imageAngle") ImageAngle imageAngle,
			@RequestParam("image") MultipartFile imageFile) throws Exception {

		if (imageFile.isEmpty()) {
			return ResponseEntity.badRequest().body(Collections.singletonMap("image", "failed"));
		}
		// Assuming you have a service to handle this upload.
		createUserLicenseService.uploadLicenseImage(licenseId, imageFile, imageAngle);
		return ResponseEntity.ok(Collections.singletonMap("image", "success"));
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
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<?> getUserWithDetails(Pageable pageable) {
		return ResponseEntity.ok(userService.findAllUsersWithDetails(pageable));
	}

}
