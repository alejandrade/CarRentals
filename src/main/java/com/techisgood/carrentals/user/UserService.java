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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        Optional<DbUser> optionalDbUser = userRepository.findById(userId);
        if (optionalDbUser.isEmpty()) {
            throw new EntityNotFoundException("User with ID " + userId + " not found.");
        }

        DbUser dbUser = optionalDbUser.get();

        // Update the user's fields based on the modifiedUserDto
        dbUser.setEmail(modifiedUserDto.getEmail());
        dbUser.setPhoneNumber(modifiedUserDto.getPhoneNumber());
        dbUser.setEnabled(modifiedUserDto.getEnabled());
        userRepository.save(dbUser);

        authorityRepository.deleteAll(dbUser.getAuthorities());
        for (UserAuthority userAuthority : modifiedUserDto.getAuthorities()) {
            Authority authority = new Authority();
            authority.setUser(dbUser);
            authority.setAuthority(userAuthority.name());
        }

        serviceLocationClerkRepository.deleteAll(dbUser.getServiceLocationClerks());

        for (String s : modifiedUserDto.getServiceLocationId()) {
            ServiceLocationClerk clerk = new ServiceLocationClerk();
            clerk.setClerk(dbUser);
            ServiceLocation byId = serviceLocationRepository.findById(s).orElseThrow();
            clerk.setServiceLocation(byId);
            serviceLocationClerkRepository.save(clerk);
        }

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
