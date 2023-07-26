package com.techisgood.carrentals.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techisgood.carrentals.model.DbUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserByIdService {
	private final UserRepository userRepository;

    public Optional<DbUser> getUser(String id) {
        Optional<DbUser> optionalDbUser = userRepository.findById(id);
        return optionalDbUser;
    }
}
