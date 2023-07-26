package com.techisgood.carrentals.user;

import com.techisgood.carrentals.authorities.AuthoritiesCreateService;
import com.techisgood.carrentals.authorities.UserAuthority;
import com.techisgood.carrentals.model.DbUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.techisgood.carrentals.user.UserNameValidator.isEmail;

@Service
@RequiredArgsConstructor
public class UserCreateIfNotExistServiceImpl implements UserCreateIfNotExistService {

    private final UserByEmailOrPhoneService useByEmailOrPhoneService;
    private final AuthoritiesCreateService authoritiesCreateService;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public DbUser createIfNoneExists(String phoneNumberEmail, UserAuthority userAuthority) {
        Optional<DbUser> user = useByEmailOrPhoneService.getUser(phoneNumberEmail);
        if (user.isEmpty()) {
            DbUser dbUser = new DbUser();
            if (isEmail(phoneNumberEmail)) {
                dbUser.setEmail(phoneNumberEmail);
            } else {
                dbUser.setPhoneNumber(phoneNumberEmail);
            }

            userRepository.save(dbUser);
            authoritiesCreateService.createAuthorityForUser(dbUser, userAuthority);
        }

        return user.get();
    }
}
