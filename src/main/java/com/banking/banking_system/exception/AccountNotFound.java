package com.banking.banking_system.exception;

public class AccountNotFound extends RuntimeException {
    public AccountNotFound() {
        super("Account ID not found");
    }
}