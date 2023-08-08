package com.techisgood.carrentals.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateContactInformation {

    private String userId;

    @NotBlank
    private String username;
}
