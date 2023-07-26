package com.techisgood.carrentals.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.techisgood.carrentals.model.DbUserDemographics;

public interface UserDemographicsRepository extends JpaRepository<DbUserDemographics, String> {
	Optional<DbUserDemographics> findByUserId(String userId);
}
