package com.bank.models;

public class AccountTransfer extends Transaction {
    private String receiverAccount;

    public AccountTransfer(String id, double amount, String accountNumber, String receiverAccount) {
        super(id, amount, accountNumber);
        this.receiverAccount = receiverAccount;
    }

    @Override
    public void process() {
        Account sender = Account.loadAccount(getAccountNumber());
        Account receiver = Account.loadAccount(receiverAccount);
        if (sender != null && receiver != null && sender.withdraw(getAmount())) {
            receiver.deposit(getAmount());
            saveToFile();
        }
    }
}