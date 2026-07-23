package com.example.users.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        @NotBlank String street,
        @NotBlank String city,
        @NotBlank String country,
        @NotBlank String zip
) {}
