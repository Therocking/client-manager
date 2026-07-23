package com.example.users.application.command;

import java.util.UUID;

public record UpdateAddressCommand(
        UUID id,
        UUID userId,
        String street,
        String city,
        String country,
        String zip
) {}
