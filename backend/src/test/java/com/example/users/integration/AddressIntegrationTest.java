package com.example.users.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class AddressIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @Autowired
    MockMvc mockMvc;

    private String userId;

    @BeforeEach
    void createUser() throws Exception {
        String resp = mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"firstname":"Alice","lastname":"Test","email":"alice%s@example.com"}
                                """.formatted(System.nanoTime())))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        userId = resp.split("\"id\":\"")[1].split("\"")[0];
    }

    @Test
    void addAndDeleteAddress() throws Exception {
        String resp = mockMvc.perform(post("/api/v1/users/" + userId + "/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"street":"123 Main St","city":"Springfield","country":"US","zip":"12345"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();

        String addressId = resp.split("\"id\":\"")[1].split("\"")[0];

        mockMvc.perform(delete("/api/v1/users/" + userId + "/addresses/" + addressId))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateAddress() throws Exception {
        String resp = mockMvc.perform(post("/api/v1/users/" + userId + "/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"street":"Old St","city":"OldCity","country":"US","zip":"00000"}
                                """))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String addressId = resp.split("\"id\":\"")[1].split("\"")[0];

        mockMvc.perform(put("/api/v1/users/" + userId + "/addresses/" + addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"street":"New St","city":"NewCity","country":"CA","zip":"99999"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("New St"))
                .andExpect(jsonPath("$.city").value("NewCity"));
    }

    @Test
    void addAddress_validationFails() throws Exception {
        mockMvc.perform(post("/api/v1/users/" + userId + "/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"street":"","city":"","country":"","zip":""}
                                """))
                .andExpect(status().isBadRequest());
    }
}
