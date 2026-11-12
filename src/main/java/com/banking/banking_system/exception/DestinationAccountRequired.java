package com.banking.banking_system.exception;

public class DestinationAccountRequired extends RuntimeException{
    public DestinationAccountRequired(){
        super("Destination account is required for transfers");
    }
}
