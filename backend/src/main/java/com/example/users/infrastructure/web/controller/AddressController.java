package com.example.users.infrastructure.web.controller;

import com.example.users.application.command.AddAddressCommand;
import com.example.users.application.command.AddressCommandService;
import com.example.users.application.command.UpdateAddressCommand;
import com.example.users.infrastructure.web.dto.request.AddressRequest;
import com.example.users.infrastructure.web.dto.response.AddressResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/addresses")
@RequiredArgsConstructor
@Tag(name = "Addresses")
public class AddressController {

    private final AddressCommandService addressCommandService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add an address to a user")
    public AddressResponse add(@PathVariable UUID userId, @Valid @RequestBody AddressRequest req) {
        return AddressResponse.from(addressCommandService.add(
                new AddAddressCommand(userId, req.street(), req.city(), req.country(), req.zip())
        ));
    }

    @PutMapping("/{addressId}")
    @Operation(summary = "Update an address")
    public AddressResponse update(
            @PathVariable UUID userId,
            @PathVariable UUID addressId,
            @Valid @RequestBody AddressRequest req) {
        return AddressResponse.from(addressCommandService.update(
                new UpdateAddressCommand(addressId, userId, req.street(), req.city(), req.country(), req.zip())
        ));
    }

    @DeleteMapping("/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an address")
    public void delete(@PathVariable UUID userId, @PathVariable UUID addressId) {
        addressCommandService.delete(userId, addressId);
    }
}
