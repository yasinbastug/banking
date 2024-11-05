package com.bank.models;

public class Withdrawal extends Transaction {
    public Withdrawal(String id, double amount, String accountNumber) {
        super(id, amount, accountNumber);
    }

    @Override
    public void process() {
        Account account = Account.loadAccount(getAccountNumber());
        if (account != null && account.withdraw(getAmount())) {
            saveToFile();
        }
    }
}
