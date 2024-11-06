package com.bank.models;

import com.bank.utils.FileUtils;

public class Deposit extends Transaction {
    public Deposit(String transactionId, String accountId, double amount) {
        super(transactionId, accountId, amount);
    }

    @Override
    public void execute() {
        Account account = FileUtils.loadAccount(accountId);
        if (account != null) {
            account.deposit(amount);
            account.save();
            FileUtils.logTransaction("Deposit", "Account: " + accountId, amount);
            System.out.println("Deposit successful. New balance: " + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }
}
