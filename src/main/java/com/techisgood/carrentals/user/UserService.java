package com.techisgood.carrentals.user;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.techisgood.carrentals.authorities.UserAuthority;
import com.techisgood.carrentals.model.ServiceLocation;
import com.techisgood.carrentals.model.ServiceLocationClerk;
import com.techisgood.carrentals.service_location.ServiceLocationRepository;
import com.techisgood.carrentals.service_location_clerk.ClerkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techisgood.carrentals.model.Authority;
import com.techisgood.carrentals.model.DbUser;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserWithDetailsDtoConverter converter;
    private final ClerkRepository clerkRepository;
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

        if (dbUser.getAuthorities().stream().map(Authority::getAuthority).anyMatch(x-> x.equals(UserAuthority.ROLE_CLERK.name()))){
            ServiceLocationClerk clerk = clerkRepository.findByUserId(dbUser.getId()).orElse(new ServiceLocationClerk());
            ServiceLocation serviceLocation = serviceLocationRepository.findById(modifiedUserDto.getServiceLocationId()).orElseThrow();
            clerk.setServiceLocation(serviceLocation);
            clerk.setUser(dbUser);
            clerk.setStatus("ACTIVE");
            clerkRepository.save(clerk);
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
