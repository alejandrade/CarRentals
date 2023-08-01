package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.model.UserInsurance;
import com.techisgood.carrentals.model.UserLicense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInsuranceRepository extends JpaRepository<UserInsurance, String> {
    List<UserInsurance> findByUserIdAndActiveTrue(String userId);
}
