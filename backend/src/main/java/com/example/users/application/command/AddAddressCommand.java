package com.example.users.application.command;

import java.util.UUID;

public record AddAddressCommand(
        UUID userId,
        String street,
        String city,
        String country,
        String zip
) {}
