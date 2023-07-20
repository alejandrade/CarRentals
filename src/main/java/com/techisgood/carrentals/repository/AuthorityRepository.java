package com.techisgood.carrentals.repository;

import com.techisgood.carrentals.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
    List<Authority> findByUserId(String userId);

}
