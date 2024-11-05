package com.bank.models;

public class Deposit extends Transaction {
    public Deposit(String id, double amount, String accountNumber) {
        super(id, amount, accountNumber);
    }

    @Override
    public void process() {
        Account account = Account.loadAccount(getAccountNumber());
        if (account != null) {
            account.deposit(getAmount());
            saveToFile();
        }
    }
}

