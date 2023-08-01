package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.UserInsurance;
import com.techisgood.carrentals.model.UserLicense;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActiveUserDataService {
    private final UserRepository userRepository;
    private final UserLicenseRepository userLicenseRepository;
    private final UserInsuranceRepository userInsuranceRepository;


    @Transactional(readOnly = true)
    public UserDto getCurrentUser(String userId) {
        UserDto userDto = userRepository.findById(userId).map(UserDto::from).orElseThrow();
        Optional<UserInsurance> insurance = userInsuranceRepository.findByUserIdAndActiveTrue(userId).stream().findAny();
        insurance.ifPresent(x -> userDto.setUserInsurances(List.of(UserInsuranceDto.from(x))));
        Optional<UserLicense> userLicense = userLicenseRepository.findByUserIdAndActiveTrue(userId).stream().findAny();
        userLicense.ifPresent(x -> userDto.setUserLicenses(List.of(UserLicenseDto.from(x))));
        return userDto;
    }
}
