package com.techisgood.carrentals.authorities;

import com.techisgood.carrentals.model.Authority;
import com.techisgood.carrentals.model.DbUser;
import org.springframework.transaction.annotation.Transactional;

public interface AuthoritiesCreateService {
    @Transactional
    Authority createAuthorityForUser(DbUser user, UserAuthority userAuthority);
}
