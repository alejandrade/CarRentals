package com.techisgood.carrentals.user;

import static com.techisgood.carrentals.security.SecurityUtils.isAdminClerkOrSameUser;

import java.util.Collections;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techisgood.carrentals.exception.RemoteServiceException;
import com.techisgood.carrentals.model.DbUser;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
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
	private final UserRepository userRepository;

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

	@GetMapping("/client/{phoneNumber}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') || hasAuthority('ROLE_CLERK')")
	public UserDto getClient(@PathVariable @Pattern(regexp = "^\\+?[0-9]{10,15}$") String phoneNumber) {
		DbUser dbUser = userRepository.findByPhoneNumber(phoneNumber.replace(" ", "")).orElseThrow();
		return activeUserDataService.getCurrentUser(dbUser.getId());
	}

	@GetMapping("/current")
	public UserDto getLoggedInUser(@AuthenticationPrincipal UserDetails userDetails) {
		return activeUserDataService.getCurrentUser(userDetails.getUsername());
	}

	@PostMapping("/contactInformation")
	public UserDto updateContactInformation(@RequestBody @Valid UpdateContactInformation updateContactInformation, @AuthenticationPrincipal UserDetails userDetails) {
		isAdminClerkOrSameUser(updateContactInformation.getUserId(), userDetails);
		return userUpdateContactInformationService.update(updateContactInformation);
	}

	@PostMapping("/insurance")
	public UserInsuranceDto createInsurance(@Valid @RequestBody UserInsuranceDto userInsuranceDto, @AuthenticationPrincipal UserDetails userDetails) {
		isAdminClerkOrSameUser(userInsuranceDto.getUserId(), userDetails);
		return UserInsuranceDto.from(createUserInsuranceService.save(userInsuranceDto));
	}


	@PostMapping("/insurance/upload")
	public ResponseEntity<?> uploadInsuranceImage(
			@RequestParam("userId") String userId,
			@RequestParam("insuranceId") String insuranceId,
			@RequestParam("imageAngle") ImageAngle imageAngle,
			@RequestParam("image") MultipartFile imageFile,
			@AuthenticationPrincipal UserDetails userDetails) throws Exception {
		isAdminClerkOrSameUser(userId, userDetails);

		if (imageFile.isEmpty()) {
			return ResponseEntity.badRequest().body(Collections.singletonMap("image", "failed"));
		}

		// Assuming you have a service to handle this upload.
		createUserInsuranceService.uploadInsuranceImage(userId, insuranceId, imageFile, imageAngle);
		return ResponseEntity.ok(Collections.singletonMap("image", "success"));
	}

	@PostMapping("/license")
	public UserLicenseDto createLicense(@Valid @RequestBody UserLicenseDto userInsuranceDto,
										@AuthenticationPrincipal UserDetails userDetails) {
		isAdminClerkOrSameUser(userInsuranceDto.getUserId(), userDetails);
		return UserLicenseDto.from(createUserLicenseService.save(userInsuranceDto));
	}

	@PostMapping("/license/upload")
	public ResponseEntity<?> uploadLicenseImage(
			@RequestParam("userId") String userId,
			@RequestParam("licenseId") String licenseId,
			@RequestParam("imageAngle") ImageAngle imageAngle,
			@RequestParam("image") MultipartFile imageFile,
			@AuthenticationPrincipal UserDetails userDetails) throws Exception {
		isAdminClerkOrSameUser(userId, userDetails);

		if (imageFile.isEmpty()) {
			return ResponseEntity.badRequest().body(Collections.singletonMap("image", "failed"));
		}
		// Assuming you have a service to handle this upload.
		createUserLicenseService.uploadLicenseImage(userId, licenseId, imageFile, imageAngle);
		return ResponseEntity.ok(Collections.singletonMap("image", "success"));
	}
	
	@PostMapping("/demographic")
	public ResponseEntity<?> createUserDemographics(@Valid @RequestBody UserDemographicsDto requestBody, @AuthenticationPrincipal UserDetails userDetails) throws RemoteServiceException {
		isAdminClerkOrSameUser(requestBody.getUserId(), userDetails);
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
