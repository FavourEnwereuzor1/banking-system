package com.banking.banking_system.exception;

public class AccountAlreadyExist extends RuntimeException {
    public AccountAlreadyExist() {
        super("Account number already exists");
    }
}
