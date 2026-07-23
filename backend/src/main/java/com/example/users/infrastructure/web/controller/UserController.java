package com.example.users.infrastructure.web.controller;

import com.example.users.application.command.CreateUserCommand;
import com.example.users.application.command.UpdateUserCommand;
import com.example.users.application.command.UserCommandService;
import com.example.users.application.query.UserQueryService;
import com.example.users.infrastructure.web.dto.request.CreateUserRequest;
import com.example.users.infrastructure.web.dto.request.UpdateUserRequest;
import com.example.users.infrastructure.web.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @GetMapping
    @Operation(summary = "List all users")
    public List<UserResponse> list() {
        return userQueryService.findAll().stream()
                .map(UserResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public UserResponse get(@PathVariable UUID id) {
        return UserResponse.from(userQueryService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a user")
    public UserResponse create(@Valid @RequestBody CreateUserRequest req) {
        return UserResponse.from(userCommandService.create(
                new CreateUserCommand(req.firstname(), req.lastname(), req.email(), req.photo())
        ));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user")
    public UserResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest req) {
        return UserResponse.from(userCommandService.update(
                new UpdateUserCommand(id, req.firstname(), req.lastname(), req.email(), req.photo())
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a user")
    public void delete(@PathVariable UUID id) {
        userCommandService.delete(id);
    }
}
