package com.banking.banking_system.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(@NotBlank(message = "First name is required")
                                 @Size(max = 100)
                                 String firstName,

                                 @NotBlank(message = "Last name is required")
                                 @Size(max = 100)
                                 String lastName,

                                 @NotBlank(message = "Email is required")
                                 @Email(message = "Invalid email address")
                                 @Size(max = 255)
                                 String email,

                                 @Size(max = 20)
                                 String phoneNumber,

                                 @NotBlank(message = "Password is required")
                                 @Size(min = 8, max = 72,
                                         message = "Password must be between 8 and 72 characters")
                                 String password) {
}
