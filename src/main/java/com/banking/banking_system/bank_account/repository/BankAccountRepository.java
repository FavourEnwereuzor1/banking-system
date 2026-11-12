package com.banking.banking_system.bank_account.repository;

import com.banking.banking_system.bank_account.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository
        extends JpaRepository<BankAccount, Long> {
    boolean existsByAccountNumber(String accountNumber);
}
