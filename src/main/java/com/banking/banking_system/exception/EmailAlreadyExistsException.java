package com.banking.banking_system.exception;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String email) {
        super("Email is already in use: " + email);
    }
}
