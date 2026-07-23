package com.example.users.application.command;

import com.example.users.domain.model.Address;
import com.example.users.domain.model.User;
import com.example.users.domain.repository.AddressRepository;
import com.example.users.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressCommandService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Transactional
    public Address add(AddAddressCommand cmd) {
        User user = userRepository.findById(cmd.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + cmd.userId()));
        Address address = Address.builder()
                .street(cmd.street())
                .city(cmd.city())
                .country(cmd.country())
                .zip(cmd.zip())
                .user(user)
                .build();
        return addressRepository.save(address);
    }

    @Transactional
    public Address update(UpdateAddressCommand cmd) {
        Address address = addressRepository.findById(cmd.id())
                .orElseThrow(() -> new EntityNotFoundException("Address not found: " + cmd.id()));
        if (!address.getUser().getId().equals(cmd.userId())) {
            throw new EntityNotFoundException("Address not found for user: " + cmd.userId());
        }
        address.setStreet(cmd.street());
        address.setCity(cmd.city());
        address.setCountry(cmd.country());
        address.setZip(cmd.zip());
        return addressRepository.save(address);
    }

    @Transactional
    public void delete(UUID userId, UUID addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found: " + addressId));
        if (!address.getUser().getId().equals(userId)) {
            throw new EntityNotFoundException("Address not found for user: " + userId);
        }
        addressRepository.deleteById(addressId);
    }
}
