package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.DbUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.techisgood.carrentals.user.UserNameValidator.isEmailOrPhoneNumber;

@Service
@RequiredArgsConstructor
public class UserByEmailOrPhoneServiceImpl implements UserByEmailOrPhoneService {
    private final UserRepository userRepository;

    @Override
    public Optional<DbUser> getUser(String input) {
        if (!isEmailOrPhoneNumber(input)) {
            throw new IllegalArgumentException("Not email or phone number");
        }

        Optional<DbUser> optionalDbUser = userRepository.findByEmail(input);

        if (optionalDbUser.isEmpty()) {
            optionalDbUser = userRepository.findByPhoneNumber(input);
        }

        return optionalDbUser;
    }
}
