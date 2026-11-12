package com.banking.banking_system.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record CustomerDto (Long id,
                           @NotBlank(message = "First name cannot be empty")
                           @Size(max = 100, message = "First name cannot exceed 100 characters")
                            String firstName,
                           @NotBlank(message = "Last name cannot be empty")
                           @Size(max = 100, message = "Last name cannot exceed 100 characters")
                            String lastName,
                           @NotBlank(message = "Email cannot be empty")
                           @Email(message = "Invalid email address")
                           @Size(max = 255, message = "Email cannot exceed 255 characters")
                            String email,
                           @Size(max = 20, message = "Phone number cannot exceed 20 characters")
                            String phoneNumber,
                            Instant createdAt) {
}
