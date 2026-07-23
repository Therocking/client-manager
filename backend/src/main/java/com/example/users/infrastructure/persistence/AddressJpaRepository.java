package com.example.users.infrastructure.persistence;

import com.example.users.domain.model.Address;
import com.example.users.domain.repository.AddressRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressJpaRepository extends JpaRepository<Address, UUID>, AddressRepository {
}
