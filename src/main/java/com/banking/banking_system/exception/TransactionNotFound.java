package com.banking.banking_system.exception;

public class TransactionNotFound extends RuntimeException{
    public TransactionNotFound(){
        super("Transaction Id no found");
    }
}
