package com.bank.models;

import com.bank.utils.FileUtils;

public class Withdrawal extends Transaction {
    public Withdrawal(String transactionId, String accountId, double amount) {
        super(transactionId, accountId, amount);
    }

    @Override
    public void execute() {
        Account account = FileUtils.loadAccount(accountId);
        if (account != null) {
            if (account.withdraw(amount)) {
                account.save();
                FileUtils.logTransaction("Withdrawal", "Account: " + accountId, amount);
                System.out.println("Withdrawal successful. New balance: " + account.getBalance());
            } else {
                System.out.println("Insufficient funds.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }
}
