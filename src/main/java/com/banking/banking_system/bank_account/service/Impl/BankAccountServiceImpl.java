package com.banking.banking_system.bank_account.service.Impl;

import com.banking.banking_system.bank_account.dto.BankAccountDto;
import com.banking.banking_system.bank_account.entity.BankAccount;
import com.banking.banking_system.bank_account.repository.BankAccountRepository;
import com.banking.banking_system.bank_account.service.IBankAccountService;
import com.banking.banking_system.customer.entity.Customer;

import com.banking.banking_system.customer.repository.CustomerRepository;
import com.banking.banking_system.exception.AccountAlreadyExist;
import com.banking.banking_system.exception.AccountNotFound;
import com.banking.banking_system.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements IBankAccountService {
   final private BankAccountRepository bankAccountRepository;
   final private CustomerRepository customerRepository;

    @Override
    @Transactional
    public BankAccountDto saveBankAccount(BankAccountDto dto) {
        if (bankAccountRepository.existsByAccountNumber(
                dto.accountNumber())) {
            throw new AccountAlreadyExist();
        }

        Customer customer = customerRepository.findById(
                dto.customerId()
        ).orElseThrow(() ->
               new CustomerNotFoundException(dto.customerId()));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(dto.accountNumber());
        bankAccount.setAccountType(dto.accountType());
        bankAccount.setCustomer(customer);

        // The client must not choose the opening balance.
        bankAccount.setBalance(java.math.BigDecimal.ZERO);

        BankAccount savedAccount =
                bankAccountRepository.save(bankAccount);

        return convertToDto(savedAccount);
    }


    @Override
    @Transactional(readOnly = true)
    public BankAccountDto findBankAccountById(Long id) {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFound());

        return convertToDto(bankAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankAccountDto> findAllBankAccounts() {
        return bankAccountRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public BankAccountDto updateBankAccount(Long id, BankAccountDto dto) {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFound());
        if (!bankAccount.getAccountNumber().equals(dto.accountNumber()) && bankAccountRepository.existsByAccountNumber(
                dto.accountNumber())) {
            throw new AccountAlreadyExist();
        }

        bankAccount.setAccountNumber(dto.accountNumber());
        bankAccount.setAccountType(dto.accountType());

        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        return convertToDto(savedAccount);

    }

    @Override
    public BankAccountDto patchBankAccount(Long id, BankAccountDto dto) {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFound());
        if(dto.accountNumber() != null){
            if (!bankAccount.getAccountNumber().equals(dto.accountNumber()) && bankAccountRepository.existsByAccountNumber(
                    dto.accountNumber())) {
                throw new AccountAlreadyExist();
            }
            bankAccount.setAccountNumber(dto.accountNumber());
        }
        if(dto.accountType() != null){
            bankAccount.setAccountType(dto.accountType());
        }

        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        return convertToDto(savedAccount);
    }

    @Override
    public void deleteBankAccount(Long id) {
        bankAccountRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return bankAccountRepository.existsById(id);
    }

    private BankAccountDto convertToDto(BankAccount bankAccount) {
        return new BankAccountDto(
                bankAccount.getId(),
                bankAccount.getAccountNumber(),
                bankAccount.getAccountType(),
                bankAccount.getBalance(),
                bankAccount.getCustomer().getId(),
                bankAccount.getCreatedAt()
        );
    }
}
