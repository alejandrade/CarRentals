package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.model.UserInsurance;
import com.techisgood.carrentals.model.UserLicense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<DbUser, String>, PagingAndSortingRepository<DbUser, String> {
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

    @Query(value = "SELECT u.id, u.email, u.phone_number, " +
            "ud.first_name, ud.last_name, " +
            "GROUP_CONCAT(a.authority SEPARATOR ','), GROUP_CONCAT(sl.id SEPARATOR ',') " +
            "FROM users u " +
            "LEFT JOIN user_demographics ud ON u.id = ud.user_id " +
            "LEFT JOIN authorities a ON u.id = a.user_id " +
            "LEFT JOIN service_location_clerk slc ON u.id = slc.user_id " +
            "LEFT JOIN service_location sl ON slc.location_id = sl.id " +
            "GROUP BY u.id, u.email, u.phone_number, ud.first_name, ud.last_name",
            nativeQuery = true)
    Page<Object[]> findAllUsersWithDetailsNative(Pageable pageable);






}
