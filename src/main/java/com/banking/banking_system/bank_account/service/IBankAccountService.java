package com.banking.banking_system.bank_account.service;

import com.banking.banking_system.bank_account.dto.BankAccountDto;
import java.util.List;

public interface IBankAccountService {
    BankAccountDto saveBankAccount(BankAccountDto dto);
    BankAccountDto findBankAccountById(Long id);
    List<BankAccountDto> findAllBankAccounts();
    BankAccountDto updateBankAccount(Long id, BankAccountDto dto);
    BankAccountDto patchBankAccount(Long id, BankAccountDto dto);
    void deleteBankAccount(Long id);
    boolean existsById(Long id);
}
