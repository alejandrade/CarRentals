package com.techisgood.carrentals.user;

import com.techisgood.carrentals.authorities.UserAuthority;
import com.techisgood.carrentals.model.DbUser;

public interface UserCreateIfNotExistService {
    DbUser createIfNoneExists(String phoneNumberEmail, UserAuthority userAuthority);
}
