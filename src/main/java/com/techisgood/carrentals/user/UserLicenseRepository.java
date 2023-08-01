package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.UserLicense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLicenseRepository extends JpaRepository<UserLicense, String> {
    List<UserLicense> findByUserIdAndActiveTrue(String userId);

}
