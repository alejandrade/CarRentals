package com.techisgood.carrentals.user;

import com.techisgood.carrentals.global.StorageService;
import com.techisgood.carrentals.model.UserLicense;
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
public class CreateUserLicenseService {
    private final UserLicenseRepository userLicenseRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final StorageService storageService;


    @Transactional
    public UserLicenseDto uploadLicenseImage(String licenseId, MultipartFile imageFile, ImageAngle angle) {
        UserLicense license = userLicenseRepository.findById(licenseId).orElseThrow();
        String upload = storageService.upload(license.getId(), imageFile);
        if (angle.equals(ImageAngle.FRONT)) {
            license.setFrontCardPicture(upload);
        } else if (angle.equals(ImageAngle.BACK)) {
            license.setBackCardPicture(upload);
        }
        userLicenseRepository.save(license);
        return UserLicenseDto.from(license);
    }

    @Transactional
    public UserLicense save(UserLicenseDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userIdFromJWT = jwtTokenProvider.getUserIdFromJWT(authentication.getCredentials().toString());

        List<UserLicense> byUserId = userLicenseRepository.findByUserIdAndActiveTrue(userIdFromJWT);
        for (UserLicense item : byUserId) {
            item.setActive(false);
            userLicenseRepository.save(item);
        }

        UserLicense item = new UserLicense();
        item.setUser(userRepository.getReferenceById(userIdFromJWT));
        item.setActive(true);
        item.setDateOfIssue(dto.getDateOfIssue());
        item.setExpirationDate(dto.getExpirationDate());
        item.setLicenseNumber(dto.getLicenseNumber());
        item.setIssuingState(dto.getIssuingState());
        return userLicenseRepository.save(item);
    }
}
