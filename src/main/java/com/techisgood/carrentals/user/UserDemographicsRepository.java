package com.techisgood.carrentals.user;

import com.techisgood.carrentals.model.DbUserDemographics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDemographicsRepository extends JpaRepository<DbUserDemographics, String> {
	Optional<DbUserDemographics> findByUserId(String userId);
}
