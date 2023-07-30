package com.techisgood.carrentals.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPhoneNumberException extends IllegalArgumentException {
    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}
