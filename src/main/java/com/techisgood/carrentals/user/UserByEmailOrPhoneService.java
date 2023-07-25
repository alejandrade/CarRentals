package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.DbUser;

import java.util.Optional;

public interface UserByEmailOrPhoneService {
    Optional<DbUser> getUser(String input);
}
