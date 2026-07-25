package com.example.users.infrastructure.persistence;

import com.example.users.domain.model.User;
import com.example.users.domain.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<User, UUID>, UserRepository {

    @Override
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.addresses")
    @NonNull List<User> findAll();

    @Override
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.addresses WHERE u.id = :id")
    @NonNull Optional<User> findById(@Param("id") @NonNull UUID id);
}
