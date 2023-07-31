package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<DbUser, String> {
	Optional<DbUser> findById(String id);
    Optional<DbUser> findByEmail(String email);
    Optional<DbUser> findByPhoneNumber(String phoneNumber);

    @Query("SELECT DISTINCT u FROM DbUser u " +
            "JOIN FETCH u.userInsurances ui " +
            "JOIN FETCH u.userDemographics ud " +
            "JOIN FETCH u.userLicenses ul " +
            "WHERE u.id = :userId AND ui.active = true AND ul.active = true")
    Optional<DbUser> findUserWithActiveInsurancesAndLicenses(String userId);

}
