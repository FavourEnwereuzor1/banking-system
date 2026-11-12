package com.banking.banking_system.transaction.dto;
import java.math.BigDecimal;
import java.time.Instant;
public record TransactionDto(
        Long id,
        String transactionType,
        BigDecimal amount,
        String description,
        Instant transactionDate,
        Long bankAccountId,
        Long destinationBankAccountId
) {}