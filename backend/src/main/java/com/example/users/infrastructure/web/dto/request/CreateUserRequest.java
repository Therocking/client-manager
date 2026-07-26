package com.example.users.infrastructure.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;

public record CreateUserRequest(
        @NotBlank String firstname,
        @NotBlank String lastname,
        @NotBlank @Email @NonNull String email,
        String photo
) {}
