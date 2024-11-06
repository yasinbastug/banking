package com.bank.models;


public class SavingsAccount extends Account {
    public SavingsAccount(String accountNumber, String userId, double balance) {
        super(accountNumber, userId, balance, "Savings");
    }
}
