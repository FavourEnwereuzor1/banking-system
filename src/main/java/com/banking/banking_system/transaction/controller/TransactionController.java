package com.banking.banking_system.transaction.controller;

import com.banking.banking_system.transaction.dto.TransactionDto;
import com.banking.banking_system.transaction.dto.TransactionRequestDto;
import com.banking.banking_system.transaction.service.ITransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Validated
public class TransactionController {

    private final ITransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDto> saveTransaction(
            @RequestBody @Valid TransactionRequestDto transactionDto) {

        TransactionDto savedTransaction =
                transactionService.saveTransaction(transactionDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> findTransactionById(
            @PathVariable
            @Positive(message = "Transaction ID must be positive") Long id) {

        TransactionDto transaction =
                transactionService.findTransactionById(id);

        return ResponseEntity.ok(transaction);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> findAllTransactions() {

        List<TransactionDto> transactions =
                transactionService.findAllTransactions();

        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(
            @PathVariable
            @Positive(message = "Transaction ID must be positive") Long id) {

        boolean exists = transactionService.existsById(id);

        return ResponseEntity.ok(exists);
    }
}
