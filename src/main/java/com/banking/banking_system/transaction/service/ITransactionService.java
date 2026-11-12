package com.banking.banking_system.transaction.service;

import com.banking.banking_system.transaction.dto.TransactionDto;
import com.banking.banking_system.transaction.dto.TransactionRequestDto;

import java.util.List;

public interface ITransactionService {
    TransactionDto saveTransaction(
            TransactionRequestDto transactionDto);

    TransactionDto findTransactionById(Long id);

    List<TransactionDto> findAllTransactions();

    boolean existsById(Long id);
}
