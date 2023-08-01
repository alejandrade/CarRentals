package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.model.UserInsurance;
import com.techisgood.carrentals.model.UserLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<DbUser, String> {
	Optional<DbUser> findById(String id);
    Optional<DbUser> findByEmail(String email);
    Optional<DbUser> findByPhoneNumber(String phoneNumber);

    @Query("SELECT DISTINCT u FROM DbUser u " +
            "LEFT JOIN FETCH u.userDemographics ud " +
            "WHERE u.id = :userId")
    Optional<DbUser> findUserWithDemographics(String userId);

    @Query("SELECT ui FROM UserInsurance ui " +
            "WHERE ui.user.id = :userId AND ui.active = true")
    List<UserInsurance> findActiveUserInsurances(String userId);

    @Query("SELECT ul FROM UserLicense ul " +
            "WHERE ul.user.id = :userId AND ul.active = true")
    List<UserLicense> findActiveUserLicenses(String userId);

}
