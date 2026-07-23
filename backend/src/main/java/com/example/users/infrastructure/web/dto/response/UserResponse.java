package com.example.users.infrastructure.web.dto.response;

import com.example.users.domain.model.User;

import java.util.List;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String firstname,
        String lastname,
        String email,
        String photo,
        List<AddressResponse> addresses
) {
    public static UserResponse from(User user) {
        List<AddressResponse> addresses = user.getAddresses().stream()
                .map(AddressResponse::from)
                .toList();
        return new UserResponse(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPhoto(),
                addresses
        );
    }
}
