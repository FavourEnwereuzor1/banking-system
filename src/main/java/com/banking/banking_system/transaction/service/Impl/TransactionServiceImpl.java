package com.banking.banking_system.transaction.service.Impl;

import com.banking.banking_system.bank_account.entity.BankAccount;
import com.banking.banking_system.bank_account.repository.BankAccountRepository;
import com.banking.banking_system.exception.AccountNotFound;
import com.banking.banking_system.exception.DestinationAccountRequired;
import com.banking.banking_system.exception.InsufficientAccount;
import com.banking.banking_system.exception.TransactionNotFound;
import com.banking.banking_system.transaction.dto.TransactionDto;
import com.banking.banking_system.transaction.dto.TransactionRequestDto;
import com.banking.banking_system.transaction.entity.Transaction;
import com.banking.banking_system.transaction.repository.TransactionRepository;
import com.banking.banking_system.transaction.service.ITransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
public class TransactionServiceImpl implements ITransactionService {
   final private TransactionRepository transactionRepository;
   final private BankAccountRepository bankAccountRepository;
    @Override
    @Transactional
    public TransactionDto saveTransaction(TransactionRequestDto dto) {

        BankAccount bankAccount = bankAccountRepository
                .findById(dto.bankAccountId())
                .orElseThrow(() -> new AccountNotFound());

        BankAccount destinationAccount = null;

        switch (dto.transactionType()) {

            case "DEPOSIT" -> {
                bankAccount.setBalance(
                        bankAccount.getBalance().add(dto.amount())
                );
            }

            case "WITHDRAWAL" -> {
                if (bankAccount.getBalance().compareTo(dto.amount()) < 0) {
                    throw new InsufficientAccount();
                }

                bankAccount.setBalance(
                        bankAccount.getBalance().subtract(dto.amount())
                );
            }

            case "TRANSFER" -> {
                if (dto.destinationBankAccountId() == null) {
                    throw new DestinationAccountRequired();
                }

                if (bankAccount.getId().equals(
                        dto.destinationBankAccountId())) {
                    throw new IllegalArgumentException(
                            "Cannot transfer to the same account");
                }

                destinationAccount = bankAccountRepository
                        .findById(dto.destinationBankAccountId())
                        .orElseThrow(() -> new AccountNotFound());

                if (bankAccount.getBalance().compareTo(dto.amount()) < 0) {
                    throw new InsufficientAccount();
                }

                bankAccount.setBalance(
                        bankAccount.getBalance().subtract(dto.amount())
                );

                destinationAccount.setBalance(
                        destinationAccount.getBalance().add(dto.amount())
                );
            }
        }

        Transaction transaction = new Transaction();

        transaction.setTransactionType(dto.transactionType());
        transaction.setAmount(dto.amount());
        transaction.setDescription(dto.description());
        transaction.setBankAccount(bankAccount);
        transaction.setDestinationBankAccount(destinationAccount);

        Transaction savedTransaction =
                transactionRepository.save(transaction);

        return convertToDto(savedTransaction);
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionDto findTransactionById(Long id) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFound());

        return convertToDto(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionDto> findAllTransactions() {

        return transactionRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public boolean existsById(Long id) {
        return transactionRepository.existsById(id);
    }

    private TransactionDto convertToDto(Transaction transaction) {

        return new TransactionDto(
                transaction.getId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getTransactionDate(),
                transaction.getBankAccount().getId(),
                transaction.getDestinationBankAccount() != null
                        ? transaction.getDestinationBankAccount().getId()
                        : null
        );
    }
}
