package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<DbUser, String> {
    Optional<DbUser> findByEmail(String email);
    Optional<DbUser> findByPhoneNumber(String phoneNumber);
}
