package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.DbUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserByIdService {
	private final UserRepository userRepository;

    public Optional<DbUser> getUser(String id) {
        Optional<DbUser> optionalDbUser = userRepository.findById(id);
        return optionalDbUser;
    }
}
