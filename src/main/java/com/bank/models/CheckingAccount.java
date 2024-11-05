package com.bank.models;

public class CheckingAccount extends Account {
    public CheckingAccount(String accountNumber, String userId, double initialBalance) {
        super(accountNumber, "Checking", userId, initialBalance);
    }
}
