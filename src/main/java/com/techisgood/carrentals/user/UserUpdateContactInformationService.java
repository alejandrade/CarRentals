package com.techisgood.carrentals.user;

import com.techisgood.carrentals.comms.twilio.TwilioService;
import com.techisgood.carrentals.comms.twilio.TwilioVerifyResponse;
import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.security.JwtTokenProvider;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.techisgood.carrentals.user.UserNameValidator.*;


@RequiredArgsConstructor
@Service
public class UserUpdateContactInformationService {

    private final TwilioService twilioService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserDto update(UpdateContactInformation contactInformation) {

        String username = contactInformation.getUsername();
        if (!isEmailOrPhoneNumber(username)) {
            throw new IllegalArgumentException("Not email or phone number");
        }

        TwilioVerifyResponse verify = twilioService.verify(contactInformation.getCode(), username);
        if (!verify.isVerified()) {
            throw new ValidationException("validation failed");
        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userIdFromJWT = jwtTokenProvider.getUserIdFromJWT(authentication.getCredentials().toString());

        DbUser dbUser = userRepository.findById(userIdFromJWT).orElseThrow();
        if (isEmail(username)) {
            dbUser.setEmail(username);
        } else if (isPhoneNumber(username)) {
            dbUser.setPhoneNumber(userIdFromJWT);
        }

        userRepository.save(dbUser);
        return UserDto.from(dbUser);
    }
}
