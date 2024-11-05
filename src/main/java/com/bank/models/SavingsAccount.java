package com.bank.models;

public class SavingsAccount extends Account {
    public SavingsAccount(String accountNumber, String userId, double initialBalance) {
        super(accountNumber, "Savings", userId, initialBalance);
    }
}
