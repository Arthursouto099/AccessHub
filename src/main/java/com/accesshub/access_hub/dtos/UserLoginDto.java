package com.accesshub.access_hub.dtos;

import com.accesshub.access_hub.entities.User;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
