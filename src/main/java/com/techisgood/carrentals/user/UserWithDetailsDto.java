package com.techisgood.carrentals.user;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class UserWithDetailsDto {

    private String id;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private List<String> authorities;
    private List<String> serviceLocationIds;

    public UserWithDetailsDto(String id, String email, String phoneNumber, String firstName,
                              String lastName, String authorities, String serviceLocationIds) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities != null ? Arrays.asList(authorities.split(",")): null;
        this.serviceLocationIds = serviceLocationIds != null ?
                Arrays.asList(serviceLocationIds.split(",")) : null;
    }
}
