package com.example.users.application.query;

import com.example.users.domain.model.User;
import com.example.users.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }
}
