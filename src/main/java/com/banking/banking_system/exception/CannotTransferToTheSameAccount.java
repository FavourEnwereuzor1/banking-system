package com.banking.banking_system.exception;

public class CannotTransferToTheSameAccount extends RuntimeException{
    public CannotTransferToTheSameAccount(){
        super("Cannot transfer to the same account");
    }
}
