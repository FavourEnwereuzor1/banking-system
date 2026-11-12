package com.banking.banking_system.bank_account.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public record BankAccountDto(
        Long id,

        @NotBlank(message = "Account number cannot be empty")
        @Size(max = 20, message = "Account number cannot exceed 20 characters")
        String accountNumber,

        @NotBlank(message = "Account type cannot be empty")
        @Pattern(
                regexp = "CHECKING|SAVINGS",
                message = "Account type must be CHECKING or SAVINGS"
        )
        String accountType,

        @DecimalMin(value = "0.00", message = "Balance cannot be negative")
        BigDecimal balance,

        @NotNull(message = "Customer ID cannot be null")
        Long customerId,

        Instant createdAt
) {}