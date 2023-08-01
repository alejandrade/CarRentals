package com.techisgood.carrentals.user;

import com.techisgood.carrentals.global.StorageService;
import com.techisgood.carrentals.model.UserInsurance;
import com.techisgood.carrentals.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateUserInsuranceService {
    private final UserInsuranceRepository userInsuranceRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final StorageService storageService;


    @Transactional
    public UserInsuranceDto uploadInsuranceImage(String insuranceId, MultipartFile imageFile, ImageAngle angle) {
        UserInsurance userInsurance = userInsuranceRepository.findById(insuranceId).orElseThrow();
        String upload = storageService.upload(userInsurance.getId(), imageFile);
        if (angle.equals(ImageAngle.FRONT)) {
            userInsurance.setFrontCardPicture(upload);
        } else if (angle.equals(ImageAngle.BACK)) {
            userInsurance.setBackCardPicture(upload);
        }
        userInsuranceRepository.save(userInsurance);
        return UserInsuranceDto.from(userInsurance);
    }

    @Transactional
    public UserInsurance save(UserInsuranceDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userIdFromJWT = jwtTokenProvider.getUserIdFromJWT(authentication.getCredentials().toString());

        List<UserInsurance> byUserId = userInsuranceRepository.findByUserIdAndActiveTrue(userIdFromJWT);
        for (UserInsurance userInsurance : byUserId) {
            userInsurance.setActive(false);
            userInsuranceRepository.save(userInsurance);
        }

        UserInsurance userInsurance = new UserInsurance();
        userInsurance.setUser(userRepository.getReferenceById(userIdFromJWT));
        userInsurance.setProvider(dto.getProvider());
        userInsurance.setEndDate(dto.getEndDate());
        userInsurance.setPolicyNumber(dto.getPolicyNumber());
        userInsurance.setActive(true);
        UserInsurance save = userInsuranceRepository.save(userInsurance);
        return save;
    }
}
