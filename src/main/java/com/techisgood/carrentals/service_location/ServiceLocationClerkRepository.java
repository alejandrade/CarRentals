package com.techisgood.carrentals.service_location;

import com.techisgood.carrentals.model.ServiceLocation;
import com.techisgood.carrentals.model.ServiceLocationClerk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ServiceLocationClerkRepository
        extends JpaRepository<ServiceLocationClerk, String>, PagingAndSortingRepository<ServiceLocationClerk, String> {
}
