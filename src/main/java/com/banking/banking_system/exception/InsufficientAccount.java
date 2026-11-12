package com.banking.banking_system.exception;

public class InsufficientAccount extends RuntimeException{
    public InsufficientAccount(){
        super("Insufficient account balance");
    }
}
