package com.example.users.domain.repository;

import com.example.users.domain.model.Address;

import java.util.Optional;
import java.util.UUID;

public interface AddressRepository {
    Optional<Address> findById(UUID id);
    Address save(Address address);
    void deleteById(UUID id);
}
