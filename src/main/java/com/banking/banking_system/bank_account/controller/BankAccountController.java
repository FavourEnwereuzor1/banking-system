package com.banking.banking_system.bank_account.controller;

import com.banking.banking_system.bank_account.dto.BankAccountDto;
import com.banking.banking_system.bank_account.service.Impl.BankAccountServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank-accounts")
@RequiredArgsConstructor
@Validated
public class BankAccountController {
    private final BankAccountServiceImpl bankAccountService;

    @PostMapping
    public ResponseEntity<BankAccountDto> saveBankAccount( @RequestBody @Valid BankAccountDto bankAccountDto){
       BankAccountDto bankAccountDto1 = bankAccountService.saveBankAccount(bankAccountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bankAccountDto1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDto> findBankAccountById(@PathVariable @Positive(message = "BankAccount ID must be positive") Long id){
        BankAccountDto bankAccountDto1 = bankAccountService.findBankAccountById(id);
        return ResponseEntity.ok(bankAccountDto1);
    }

    @GetMapping("")
    public ResponseEntity<List<BankAccountDto>> findAllBankAccount(){
        List<BankAccountDto> bankAccountDto1 = bankAccountService.findAllBankAccounts();
        return ResponseEntity.ok(bankAccountDto1);
    }
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existBankAccountById(@PathVariable @Positive(message = "BankAccount ID must be positive") Long id){
       Boolean bankAccountDto1 = bankAccountService.existsById(id);
        return ResponseEntity.ok(bankAccountDto1);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BankAccountDto> updateBankAccountById( @PathVariable  @Positive(message = "BankAccount ID must be positive") Long id,
                                                                 @RequestBody @Valid BankAccountDto bankAccountDto){
        BankAccountDto bankAccountDto1 = bankAccountService.updateBankAccount(id,bankAccountDto);
        return ResponseEntity.ok(bankAccountDto1);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<BankAccountDto> patchBankAccountById( @PathVariable  @Positive(message = "BankAccount ID must be positive") Long id,
                                                                 @RequestBody @Valid BankAccountDto bankAccountDto){
        BankAccountDto bankAccountDto1 = bankAccountService.patchBankAccount(id,bankAccountDto);
        return ResponseEntity.ok(bankAccountDto1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BankAccountDto> deleteBankAccountById( @PathVariable  @Positive(message = "BankAccount ID must be positive") Long id){
         bankAccountService.deleteBankAccount(id);
        return ResponseEntity.noContent().build();
    }



}
