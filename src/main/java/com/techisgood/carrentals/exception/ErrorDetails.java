package com.techisgood.carrentals.exception;

import lombok.Data;

@Data
public class ErrorDetails {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
