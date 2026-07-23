package com.example.users.infrastructure.web.dto.response;

import com.example.users.domain.model.Address;

import java.util.UUID;

public record AddressResponse(
        UUID id,
        String street,
        String city,
        String country,
        String zip,
        UUID userId
) {
    public static AddressResponse from(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getZip(),
                address.getUser().getId()
        );
    }
}
