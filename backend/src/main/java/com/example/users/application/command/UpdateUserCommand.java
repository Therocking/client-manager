package com.example.users.application.command;

import java.util.UUID;

public record UpdateUserCommand(
        UUID id,
        String firstname,
        String lastname,
        String email,
        String photo
) {}
