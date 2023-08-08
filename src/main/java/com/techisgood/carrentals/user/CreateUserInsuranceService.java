package com.techisgood.carrentals.user;

import com.techisgood.carrentals.global.StorageService;
import com.techisgood.carrentals.model.UserInsurance;
import com.techisgood.carrentals.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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
    private final StorageService storageService;


    @Transactional
    public UserInsuranceDto uploadInsuranceImage(String userId, String insuranceId, MultipartFile imageFile, ImageAngle angle) {
        UserInsurance userInsurance = userInsuranceRepository.findById(insuranceId).orElseThrow();
        String id = userInsurance.getUser().getId();
        if (!id.equals(userId)) {
            throw new AccessDeniedException("Updating insurnace does not match user id");
        }
        String upload = storageService.upload(userInsurance.getId(), imageFile);
        if (angle.equals(ImageAngle.FRONT)) {
            userInsurance.setFrontCardPictureKey(upload);
        } else if (angle.equals(ImageAngle.BACK)) {
            userInsurance.setBackCardPictureKey(upload);
        }
        userInsuranceRepository.save(userInsurance);
        return UserInsuranceDto.from(userInsurance);
    }

    @Transactional
    public UserInsurance save(UserInsuranceDto dto) {
        List<UserInsurance> byUserId = userInsuranceRepository.findByUserIdAndActiveTrue(dto.getUserId());
        for (UserInsurance userInsurance : byUserId) {
            userInsurance.setActive(false);
            userInsuranceRepository.save(userInsurance);
        }

        UserInsurance userInsurance = new UserInsurance();
        userInsurance.setUser(userRepository.getReferenceById(dto.getUserId()));
        userInsurance.setProvider(dto.getProvider());
        userInsurance.setEndDate(dto.getEndDate());
        userInsurance.setPolicyNumber(dto.getPolicyNumber());
        userInsurance.setActive(true);
        return userInsuranceRepository.save(userInsurance);
    }
}
