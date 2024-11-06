package com.bank.models;

import com.bank.utils.FileUtils;

import java.util.List;

public class Account {
    private String accountId;
    private String userId;
    private double balance;
    private String type; // Type of account, e.g., "Savings" or "Checking"

    public Account(String accountId, String userId, double balance, String type) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
        this.type = type;
    }

    // Getters
    public String getAccountId() {
        return accountId;
    }

    public String getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public String getType() {
        return type;
    }

    // Deposit method to add funds to the account
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    // Withdraw method to remove funds from the account
    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        } else {
            System.out.println("Insufficient funds or invalid withdrawal amount.");
            return false;
        }
    }

    // Save or update the account entry in the file
    public void save() {
        List<String> lines = FileUtils.readAllLines("src/main/resources/data/accounts.txt");
        boolean accountFound = false;

        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts[0].equals(accountId)) {
                // Update the existing line with the current account details
                lines.set(i, accountId + "," + userId + "," + balance + "," + type);
                accountFound = true;
                break;
            }
        }

        if (!accountFound) {
            // If the account doesn't exist, add it as a new line
            lines.add(accountId + "," + userId + "," + balance + "," + type);
        }

        // Write all lines back to the file
        FileUtils.writeLines("src/main/resources/data/accounts.txt", lines, false);
    }

    @Override
    public String toString() {
        return "Account ID: " + accountId + "\n" +
               "User ID: " + userId + "\n" +
               "Balance: " + balance + "\n" +
               "Type: " + type;
    }
}
