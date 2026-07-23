package com.example.users.application.command;

public record CreateUserCommand(
        String firstname,
        String lastname,
        String email,
        String photo
) {}
