package com.techisgood.carrentals.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateContactInformation {
    @NotBlank
    private String username;

    @NotBlank
    private String code;
}
