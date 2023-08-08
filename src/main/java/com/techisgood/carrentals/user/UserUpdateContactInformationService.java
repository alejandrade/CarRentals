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

    private final UserRepository userRepository;

    @Transactional
    public UserDto update(UpdateContactInformation contactInformation) {

        String username = contactInformation.getUsername();
        if (!isEmailOrPhoneNumber(username)) {
            throw new IllegalArgumentException("Not email or phone number");
        }

        DbUser dbUser = userRepository.findById(contactInformation.getUserId()).orElseThrow();
        if (isEmail(username)) {
            dbUser.setEmail(username);
        } else if (isPhoneNumber(username)) {
            dbUser.setPhoneNumber(contactInformation.getUsername());
        }

        userRepository.save(dbUser);
        return UserDto.from(dbUser);
    }
}
