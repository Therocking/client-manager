package com.example.users.application.command;

import com.example.users.domain.model.User;
import com.example.users.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;

    @Transactional
    public User create(CreateUserCommand cmd) {
        User user = User.builder()
                .firstname(cmd.firstname())
                .lastname(cmd.lastname())
                .email(cmd.email())
                .photo(cmd.photo())
                .build();
        return userRepository.save(user);
    }

    @Transactional
    public User update(UpdateUserCommand cmd) {
        User user = userRepository.findById(cmd.id())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + cmd.id()));
        user.setFirstname(cmd.firstname());
        user.setLastname(cmd.lastname());
        user.setEmail(cmd.email());
        user.setPhoto(cmd.photo());
        return userRepository.save(user);
    }

    @Transactional
    public void delete(UUID id) {
        if (!userRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }
}
