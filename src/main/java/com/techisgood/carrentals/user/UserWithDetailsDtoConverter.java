package com.techisgood.carrentals.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserWithDetailsDtoConverter implements Converter<Object[], UserWithDetailsDto> {

    @Override
    public UserWithDetailsDto convert(Object[] source) {
        String id = (String) source[0];
        String email = (String) source[1];
        String phoneNumber = (String) source[2];
        String firstName = (String) source[3];
        String lastName = (String) source[4];
        String authorities = (String) source[5];
        String serviceLocationIds = (String) source[6];

        return new UserWithDetailsDto(id, email, phoneNumber, firstName, lastName, authorities, serviceLocationIds);
    }
}