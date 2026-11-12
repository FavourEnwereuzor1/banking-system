package com.banking.banking_system.transaction.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TransactionRequestDto(

        @NotBlank(message = "Transaction type cannot be empty")
        @Pattern(
                regexp = "DEPOSIT|WITHDRAWAL|TRANSFER",
                message = "Transaction type must be DEPOSIT, WITHDRAWAL, or TRANSFER"
        )
        String transactionType,

        @NotNull(message = "Amount cannot be null")
        @DecimalMin(
                value = "0.00",
                inclusive = false,
                message = "Amount must be greater than zero"
        )
        BigDecimal amount,

        String description,

        @NotNull(message = "Bank account ID cannot be null")
        @Positive(message = "Bank account ID must be positive")
        Long bankAccountId,

        @Positive(message = "Destination bank account ID must be positive")
        Long destinationBankAccountId
) {}