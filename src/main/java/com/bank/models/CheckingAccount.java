package com.bank.models;

public class CheckingAccount extends Account {
    public CheckingAccount(String accountNumber, String userId, double balance) {
        super(accountNumber, userId, balance, "Checking");
    }
}