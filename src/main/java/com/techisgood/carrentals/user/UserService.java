package com.techisgood.carrentals.user;

import com.techisgood.carrentals.authorities.AuthorityRepository;
import com.techisgood.carrentals.authorities.UserAuthority;
import com.techisgood.carrentals.model.*;
import com.techisgood.carrentals.service_location.ServiceLocationClerkRepository;
import com.techisgood.carrentals.service_location.ServiceLocationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserWithDetailsDtoConverter converter;
    private final ServiceLocationClerkRepository serviceLocationClerkRepository;
    private final ServiceLocationRepository serviceLocationRepository;

    public UserDto getUser(String userId) {
        return userRepository.findById(userId).map(UserDto::from).orElseThrow();
    }
    @Transactional
    public UserDto modifyUser(String userId, UserDto modifiedUserDto) {
        // Find the user in the database by userId
        DbUser dbUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));

        // Update the user's fields based on the modifiedUserDto
        dbUser.setEmail(modifiedUserDto.getEmail());
        dbUser.setPhoneNumber(modifiedUserDto.getPhoneNumber());
        dbUser.setEnabled(modifiedUserDto.getEnabled());

        // Update Authorities
        Set<String> currentAuthorityNames = dbUser.getAuthorities().stream()
                .map(Authority::getAuthority)
                .collect(Collectors.toSet());

        Set<String> dtoAuthorityNames = modifiedUserDto.getAuthorities().stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        // Remove authorities that are no longer present
        dbUser.getAuthorities().removeIf(auth -> !dtoAuthorityNames.contains(auth.getAuthority()));

        // Add new authorities
        for (String name : dtoAuthorityNames) {
            if (!currentAuthorityNames.contains(name)) {
                Authority authority = new Authority();
                authority.setUserId(dbUser.getId());
                authority.setAuthority(name);
                dbUser.getAuthorities().add(authority);
            }
        }

        // Update ServiceLocationClerks
        Set<String> currentServiceLocationIds = dbUser.getServiceLocationClerks().stream()
                .map(ServiceLocationClerk::getServiceLocationId)
                .collect(Collectors.toSet());

        // Remove ServiceLocationClerks that are no longer present
        dbUser.getServiceLocationClerks().removeIf(clerk -> !modifiedUserDto.getServiceLocationId().contains(clerk.getServiceLocationId()));

        // Add new ServiceLocationClerks
        for (String serviceLocationId : modifiedUserDto.getServiceLocationId()) {
            if (!currentServiceLocationIds.contains(serviceLocationId)) {
                ServiceLocationClerk clerk = new ServiceLocationClerk();
                ServiceLocation serviceLocation = serviceLocationRepository.findById(serviceLocationId).orElseThrow();
                clerk.setClerk(dbUser);
                clerk.setServiceLocation(serviceLocation);
                dbUser.getServiceLocationClerks().add(clerk);
            }
        }

        // Save the user
        userRepository.save(dbUser);

        return UserDto.from(dbUser);
    }



    public Page<UserWithDetailsDto> findAllUsersWithDetails(Pageable pageable) {
        Page<Object[]> pageResult = userRepository.findAllUsersWithDetailsNative(pageable);
        List<UserWithDetailsDto> users = pageResult.getContent().stream()
                .map(converter::convert)
                .collect(Collectors.toList());
        return new PageImpl<>(users, pageable, pageResult.getTotalElements());
    }

}
