package com.techisgood.carrentals.authorities;

import com.techisgood.carrentals.model.Authority;
import com.techisgood.carrentals.model.DbUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthoritiesCreateServiceImpl implements AuthoritiesCreateService {
    private final AuthorityRepository authorityRepository;
    @Override
    @Transactional
    public Authority createAuthorityForUser(DbUser user, UserAuthority userAuthority) {
        Authority authority = new Authority();
        authority.setUserId(user.getId());
        authority.setAuthority(userAuthority.name());
        return authorityRepository.save(authority);
    }
}
