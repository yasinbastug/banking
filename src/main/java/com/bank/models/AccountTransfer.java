package com.bank.models;

import com.bank.utils.FileUtils;

public class AccountTransfer extends Transaction {
    private String receiverAccountId;

    public AccountTransfer(String transactionId, String accountId, double amount, String receiverAccountId) {
        super(transactionId, accountId, amount);
        this.receiverAccountId = receiverAccountId;
    }

    @Override
    public void execute() {
        Account sender = FileUtils.loadAccount(accountId);
        Account receiver = FileUtils.loadAccount(receiverAccountId);
        if (sender != null && receiver != null) {
            if (sender.withdraw(amount)) {
                receiver.deposit(amount);
                sender.save();
                receiver.save();
                String details = "From: " + accountId + " To: " + receiverAccountId;
                FileUtils.logTransaction("Transfer", details, amount);
                System.out.println("Transfer successful. New balances:");
                System.out.println("Sender (" + accountId + "): " + sender.getBalance());
                System.out.println("Receiver (" + receiverAccountId + "): " + receiver.getBalance());
            } else {
                System.out.println("Insufficient funds for transfer.");
            }
        } else {
            System.out.println("One or both accounts not found.");
        }
    }
}
